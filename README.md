# Kubernetes Feature Toggles

A feature toggle lets us switch the behaviour of an application without restart.
If you have a look for the rich feature set of what today is called a feature toggle,
you won't find any of them in this project. 

This implementations aim is

- multiple instances to be in sync
- Keep the current state in memory
- Use existing mechanisms of the environment

## Multiple instances to be in sync
This one is easy. When multiple instances are deployed, the feature toggle
must have the same value for all instances and all instances must behave the same.

## keep the current state in memory
No matter how fast the mechanism or technology might be, memory will always be quicker
and tend to fail lesser than any external call.
Another consideration is that if check the value on a per call rate,
we will have a busy network that - given enough instances - will fail eventually. 
But this comes with a price. We need to be sure that the cached value is up-to-date
and if the synchronisation of the feature toggles fail, we should not longer accept requests. 
 
## Use existing mechanisms of the environment
In a kubernetes cluster, just install helm and you can have databases, 
distributed caching and whatnot ready to be rolled out. This is nice but
you will find out, that not knowing the things you deploy in depth will
ruin you days when it does not behave as you expect, or if it stops working at all.
Event if you are an expert, maybe you do not want another thing to maintain,
care or heal if it's broken.
So the aim of using already existing mechanisms of the environment is a limitation
I happily check, before adding just another thing to a kubernetes cluster.

# configMaps
Kubernetes operates with a highly available, clustered data storage that goes under
the name zook... ahm etcd. 
If you look up the details it allows us to store 1 megabyte of payload per document. 
Kubernetes allows us to query, update and watch data.

So we are going with a configMap and watch its changes.


To make things as easy as possible - I want to change the map via `kubectl edit cm` -
we just add key-value pairs as payload instead of a file definition:

```
kind: ConfigMap
apiVersion: v1
metadata:
  name: feature-toggles
data:
  my-toggle: "false"
  another-toggle: "true"
```

There is only one limitation that way. We can only provide `strings` as value but that's okay.
We just will interpret the value as `on` and `off`.

| Toggle ON |
|:---------:|
|"true"     |
|"1"        |
|"y"        |
|"x"        |

| Toggle OFF |
|:----------:|
|"false"     |
|"0"         |
|"n"         |
|"-"         |

# Demo
This demo provides a swagger interface to switch the feature toggles. 
A recurring task will log the current state of `my-toggle` every 5 seconds.


# Run

ß. Deploy the configMap

`kubectl apply -f feature-toggles.yaml`


A. Build the software

`mvn clean package` 

B. Build the docker container

`docker build . --file src/main/docker/Dockerfile -t <your-repo>:<your-tag> --no-cache `

C. Push the docker tag

`docker push <your-repo>:<your-tag> `

D. Update the manifest to the repo:tag you used

E. Roll-out your application

`kubectl apply -f src/main/k8s/feature-toggles-v1.yaml`

F. Port-Forward 8080 to you application and open in a browser



G. Expected logs

```
Tomcat started on port(s): 8080 (http) with context path ''
Started Application in 7.139 seconds (JVM running for 7.989)
Toggle state: false
Toggle state: false
Toggle state: false
Toggle state: false
```

Set the my-toggle value to `"true"`.
```
$ kubectl edit cm feature-toggles
```

```
Received update for configMap 'feature-toggles'.
Toggle state: true
Toggle state: true
```


# Discussion

A. Why not using spring boot kubernetes config stuff?

Well I tried it out and it worked for me for starting the application, but the config map changes 
did not find their way into the application. 
And honestly, I really like to have some cluster specific configuration within the cluster, but
in my opinion application configuration should be provided within the application.


B. Provide controller interface to update the feature toggle?

This is super easy with the kubernetes client
```
client.configMaps().inNamespace(namespace).withName(configMap).patch(<data>);
```

I like a dev team members to get their hands dirty with kubernetes. So why provide a rest interface
when you could just `kubectl edit cm feature-toggle` and flip the switch? 

C. Why not use <your-company-product-here>?

Feature toggle provide way more. If the switch is on or off can be decided based 
on the machine, the customer, the time, the current resource consumption, the current load and whatnot.
That was not the point of the project.


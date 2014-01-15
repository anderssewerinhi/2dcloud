A simple demo of distributed animation

Example: 
Master node is on 192.168.1.100. Two client nodes are on 192.168.1.101 and 
192.168.1.102


Run the Master first with parameters <ip-of-client-1> <ip-of-client-2> ...

	MasterNode 192.168.1.101 192.168.1.102

Now you can start the client node(s) with parameter <ip-of-master-node>

	ClientNode 192.168.1.100

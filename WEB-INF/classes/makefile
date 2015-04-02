JCC = javac
JFLAGS = -g

default: BaseLayer.class LoginLayer.class ManagementModule.class MyAccountLayer.class RISBusinessLayer.class ReportModule.class UniqueID.class SearchLayer.class

BaseLayer.class: com/BaseLayer.java
	$(JCC) $(JFLAGS) com/BaseLayer.java

LoginLayer.class: com/LoginLayer.java
	$(JCC) $(JFLAGS) com/LoginLayer.java

ManagementModule.class: com/ManagementModule.java
	$(JCC) $(JFLAGS) com/ManagementModule.java

MyAccountLayer.class: com/MyAccountLayer.java
	$(JCC) $(JFLAGS) com/MyAccountLayer.java

RISBusinessLayer.class: com/RISBusinessLayer.java
	$(JCC) $(JFLAGS) com/RISBusinessLayer.java

ReportModule.class: com/ReportModule.java
	$(JCC) $(JFLAGS) com/ReportModule.java

UniqueID.class: com/UniqueID.java
	$(JCC) $(JFLAGS) com/UniqueID.java
	
SearchLayer.class: com/SearchLayer.java
	$(JCC) $(JFLAGS) com/SearchLayer.java



clean: 
	$(RM) com/*.class

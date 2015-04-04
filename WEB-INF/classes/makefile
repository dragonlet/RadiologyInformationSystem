JCC = javac
JFLAGS = -g

default: BaseLayer.class DataAnalysisModule.class BaseLayerException.class Person.class UploadLayer.class LoginLayer.class ManagementModule.class MyAccountLayer.class RISBusinessLayer.class ReportModule.class UniqueID.class SearchLayer.class OlapShelf.class

BaseLayer.class: com/BaseLayer.java
	$(JCC) $(JFLAGS) com/BaseLayer.java
	
DataAnalysisModule.class
	$(JCC) $(JFLAGS) com/DataAnalysisModule.java
	
BaseLayerException.class
	$(JCC) $(JFLAGS) com/BaseLayerException.java
	
Person.class
	$(JCC) $(JFLAGS) com/Person.java
	
UploadLayer.class
	$(JCC) $(JFLAGS) com/UploadLayer.java

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

OlapShelf.class: com/OlapShelf.java
	$(JCC) $(JFLAGS) com/OlapShelf.java


clean: 
	$(RM) com/*.class

# Lab 4. Enterprise Integration Patterns

This laboratory will consist of a series of tasks designed to familiarize you with the concept 
[Enterprise Integration Pattern](http://www.eaipatterns.com/). It is expected that students use the online 
documentation of the [Apache Camel](http://camel.apache.org/index.html) integration framework during the laboratory extensively.

The code is build using maven, which is a project building solution 

## Install `eip/reportincident`

The [report incident](http://camel.apache.org/tutorial-example-reportincident.html) is a tutorial of the Apache Camel integration framework inspired by a real life use-case. The target reader is person whom hasn't experience or just started using Camel. `reportincident` contains a modified version of the application built with this tutorial. Install it in your development environment. However, the core of the solution (aproximately just 10 lines) has been *accidentally* deleted. Our goal is to fix this situation following the next steps.

## Route `web service` to `file backup`

Parts [1](http://camel.apache.org/tutorial-example-reportincident-part1.html) and [2](http://camel.apache.org/tutorial-example-reportincident-part2.html) of the *report incident tutorial* describe how to set up a Web service that recieve a Report Incident message and then store it in a file backup with Apache Camel. Follow the tutorial using `reportincident` as blueprint. In case of doubt ask to the teacher.

## Route `file backup` to `mail`

Part [3](http://camel.apache.org/tutorial-example-reportincident-part3.html) of the *report incident tutorial* explain how to send a mail message automatically when the application detects a new message in the file backup. Follow the tutorial using `reportincident` as blueprint. In case of doubt ask to the teacher.

## Camel routes

Previous are too verbose and our goal is a 10 lines solution as a *camel route*. Parts [4](http://camel.apache.org/tutorial-example-reportincident-part4.html) and [5](http://camel.apache.org/tutorial-example-reportincident-part5.html) of the *report incident tutorial*
provides the clues to fix `-reportincident`. 


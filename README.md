# This Bootiful War Deployment of Mine
This project demonstrates how you can deploy multiple executable war files onto the same 
Tomcat instance, and still have individualized external application configuration 
**and** logging configuration.

**tldr;** Just create a custom context.xml for each war application, and have it/them declare and load the 
application.yml and logback-spring.xml onto the classpath for _that war only_.
 
This solution is simple, non-invasive (no coding) and does not rely on anything in Spring. 
It only uses the standard servlet specification implementation from whichever container you are 
using for deployments. 

If you are unfamiliar, or need a more in-depth explanation, please read on.

## Setup Instructions

To really understand what you have to do, first you have to understand what __not__ to do.

#### What NOT to do

1. **Though shalt not put any configuration in your war file**
 
    - While some configuration can be placed inside of the application(.yml|.properties), 
    such as 'spring.application.name', you should definitely never put any 'logging.*' values inside of it,
    nor should logback XML ever be placed in the 'main/resources' folders.
    - For the purposes of **testing only**, you can and should put the required configuration inside of the 
    test/resources folder.
    
2.  **Thou shalt not use the SpringBootServletInitializer** 

    - So that's a bit dramatic, so let me explain. 
    - Using the SpringBootServletInitializer _is needed_ for the general configuration and initialization 
    of the war, but it does not work for loading external logging configuration.
    - Logging configuration happens very early in the context initialization, and will have already been 
    initialized by the time the servlet initializer gets called.
    - While it is possible to load most of the spring boot configuration, and all of your 
    custom properties, it will NOT be able to load and configure logging. 
    
2.  ###Thou shalt not use the 'EnvironmentPostProcessor' and 'spring.factories'

    - If you know anything about this approach then you may be surprised, but I assure you that it does not work 
    for logging configuration resources. 
    - Everything else (that I've seen so far) will be loaded properly, but **logging wil _NOT_ work** ,
    - This can be confusing, especially since the Spring Boot team has suggested doing it this way in the past.
    - Logging configuration happens very early in the context initialization, and will have 
    already been initialized by the time the EnvironmentPortProcessor is called.      

#### What to _actually_ do 

You will need to perform the following steps for each war deployed to Tomcat.

1. Under "${catalina.home}/conf/Catalina/localhost/" create a file named after the application context
   of the war after it has been deployed. 
    - Example: If your war is called "hello.war", and/or deployed to the "hello" folder under 
    "${catalina.home}/webapps" (or wherever you have your appBase), hen the corresponding XML 
    file will be "${catalina.home}/conf/Catalina/localhost/**hello.xml**".
    
    
1.  
 



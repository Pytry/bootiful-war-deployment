# This Bootiful War Deployment of Mine
This project demonstrates how you can deploy multiple executable war files onto the same 
Tomcat instance, and still have individualized external application configuration 
**and** logging configuration.

**tldr;** Just create a custom context.xml for each war application, and have it/them declare and load the 
application.yml and logback-spring.xml onto the classpath for _that war only_.
 
This solution is simple, non-invasive (no coding), and does not rely on anything in Spring. That means that
aside from the fact that you are deploying a 'war' file, you can take full advantage of everything 
that Spring Boot has to offer.

**Example XML for 'conf/Catalina/localhost/hello.xml':**

    <?xml version='1.0' encoding='utf-8'?>
    <Context docBase="hello.war" path="hello">
      <Resources className="org.apache.catalina.webresources.StandardRoot">
        <PreResources **base="hello/config"**
                      className="org.apache.catalina.webresources.DirResourceSet"
                      internalPath="/"
                      webAppMount="/WEB-INF/classes"/>
      </Resources>
    </Context>
    
If you are unfamiliar with the 'context.xml' in Tomcat, or need a more in-depth explanation, please read on.

## Setup Instructions

To really understand what you have to do, first you have to understand what __not__ to do.

#### What thou shalt NOT do

1. **Thou shalt not put any configuration in your war file**
 
    - This is akin to having a 'catch(Exception e){}' in your code. While some 'non-configurable' configuration can be 
    placed inside of the application(.yml|.properties), such as 'spring.application.name', you should definitely never 
    put any 'logging.*' values inside of it, nor should logback XML ever be placed in the 'main/resources' folders. 
    If the configuration can or should be changed by operations or depending on whichever profile or environment it 
    pertains to, then do not put default values inside the war. This "best practice" makes it easier to pinpoint errors
    that are caused by invalid properties.  
    - For the purposes of **testing only**, you can and should put the required configuration inside of the 
    test/resources folder.
    
1.  **Thou shalt not use the SpringBootServletInitializer to load configuration.** 

    - Using the SpringBootServletInitializer _is needed_ for the general initialization 
    of the war, but it does not work for loading external logging configuration.
    - Logging configuration happens very early in the context initialization, and will have already been 
    initialized by the time the servlet initializer gets called.
    - While it is possible to load most of the spring boot configuration, and all of your 
    custom properties, it will NOT be able to load and configure logging. 
    
1.  **Thou shalt not use the 'EnvironmentPostProcessor' and 'spring.factories'**

    - If you know anything about this approach then you may be surprised, but I assure you that it does not work 
    for externalizing logging configuration resources, even if your application.properties file indicates the location
    of your 'logback-spring.xml' (or whatever you are using). 
    - Everything else (that I've seen so far) will be loaded properly, but **logging wil _NOT_ work** ,
    - This can be confusing, especially since the Spring Boot team has suggested doing it this way in the past.
    - Logging configuration happens very early in the context initialization, and will have 
    already been initialized by the time the EnvironmentPortProcessor is called.      

#### What to _actually_ do 

You will need to perform the following steps for each war deployed to Tomcat.

Under "${catalina.home}/conf/Catalina/${hostname}/" create an XML file named after the application context
   of the war after it has been deployed. 
   - This file will be the standard 'context.xml', but since you have deployed it as directed above, it will only be applied
   to the single war application isntead of everything in the JVM.
   - Example: If your war is called "hello.war", and/or it is deployed to the "hello" folder under 
      "${catalina.home}/webapps" (or wherever you have your appBase) on your localhost, then the corresponding XML 
      file will be "${catalina.home}/conf/Catalina/localhost/**hello.xml**". Below is an example of the bare-minimum 
      xml you will need to include.

      
    <?xml version='1.0' encoding='utf-8'?>
    <Context>
    </Context>


Tomcat will need to know where it is getting the files and classes for the application that this context applies. In 
  the new hello.xml file, you will need to indicate from where the base directory of the documents are going to get loaded. 
  - The document base value is placed in the 'docBase' attribute of the main '<Context>' node.
  - This will be a relative path from the appbase that has been configured for your Tomcat, which will be the 
  'webapps' folder under CATALINA_HOME if you have not changed anything in the default/root configuration, and it 
  needs to point to your war file.  


    <?xml version='1.0' encoding='utf-8'?>
    <Context docBase="hello.war">
    </Context>


Tomcat also needs to know how the application is being deployed, i.e., what it's applicationContext will be inside 
  of the webapps/appBase directory.
  - This will need to be placed in the 'path' attribute of the '<Context>' node.


    <?xml version='1.0' encoding='utf-8'?>
    <Context docBase="hello.war" path="hello">      
    </Context>

 
Next, we need to add some resource loaders that will add our external configuration files to the classpath of our 
  application.
  - There are various resource loaders, but for this use case just use the StandardRoot web resource loader.

   
    <?xml version='1.0' encoding='utf-8'?>
    <Context docBase="hello.war" path="hello">
      <Resources className="org.apache.catalina.webresources.StandardRoot">
      </Resources>
    </Context>


Now define the resources you need to load inside the '<Resources>' element.
  - Because these resources need to be loaded before logging is configured, use '<PreResources>' to hold the resource 
  information


    <?xml version='1.0' encoding='utf-8'?>
    <Context docBase="hello.war" path="hello">
      <Resources className="org.apache.catalina.webresources.StandardRoot">
        <PreResources/>
      </Resources>
    </Context>

         
You can specify multiple resources or locations, but it's simpler if you just have one directory location 
  resource configured where you can add all the files in a single app-specific directory. For this, use the
  'org.apache.catalina.webresources.DirResourceSet' as the className for the '<PreResources>' node.

  
    <?xml version='1.0' encoding='utf-8'?>
    <Context docBase="hello.war" path="hello">
      <Resources className="org.apache.catalina.webresources.StandardRoot">
        <PreResources className="org.apache.catalina.webresources.DirResourceSet"/>
      </Resources>
    </Context>

    
Add the 'internalPath' attribute,  an absolute unix path that determines the path inside of the classpath needed
   to load the resource. This does not include the file or resource name.


    <?xml version='1.0' encoding='utf-8'?>
    <Context docBase="hello.war" path="hello">
      <Resources className="org.apache.catalina.webresources.StandardRoot">
        <PreResources className="org.apache.catalina.webresources.DirResourceSet"
                      internalPath="/">
      </Resources>
    </Context>

       
Indicate where the resource will be added inside of the web application with the 'webAppMount' attribute. This
  attributes value will (likely) always be '/WEB-INF/classes'.


    <?xml version='1.0' encoding='utf-8'?>
    <Context docBase="hello.war" path="hello">
      <Resources className="org.apache.catalina.webresources.StandardRoot">
        <PreResources className="org.apache.catalina.webresources.DirResourceSet"
                      internalPath="/"
                      webAppMount="/WEB-INF/classes"/>
      </Resources>
    </Context>

      
Add a 'base' attribute with a directory path value for the directory that the 'DirResourceSet' will add to 
  the classpath.

  
    <?xml version='1.0' encoding='utf-8'?>
    <Context docBase="hello.war" path="hello">
      <Resources className="org.apache.catalina.webresources.StandardRoot">
        <PreResources base="hello/config"
                      className="org.apache.catalina.webresources.DirResourceSet"
                      internalPath="/"
                      webAppMount="/WEB-INF/classes"/>
      </Resources>
    </Context>

      
#### Done!



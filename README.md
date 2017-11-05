Sample Payment Plugin
=====================

This project provides a sample payment plugin for use with [NetDimensions
Learning](http://www.netdimensions.com/talent-management-suite/learning-management-system-lms.php),
implemented as a Java web application. Note that this code is intended primarily
to demonstrate the protocol by which the plugin communicates with NetDimensions
Learning. It does **not** validate payment information against a real payment
gateway; nor does it collect payment information from the user.

The sample payment plugin requires an application server that supports the Java
Servlet 2.5 specification, such as Apache Tomcat 6.0 or higher.

You may adapt this code to create your own payment plugin for NetDimensions
Learning.

Deployment
----------

A WAR file is available
[here](https://www.dropbox.com/s/ezf2s2uu6r8hau3/sample-payment-plugin-v3.war?dl=0),
or you can build from source using Maven.

You can deploy the WAR file to your application server in the usual way. For
example, if you are using Apache Tomcat, you can copy the WAR file to Tomcat's
`webapps` directory.

Version 3 of the plugin can connect to any number of NetDimensions Learning
sites. You will need to configure a master key that the plugin will use to
generate site-specific keys. (The site-specific key is
`HMAC_SHA256(master_key, url)`, where `url` is the base URL of the NetDimensions
Learning site.)
  
On Tomcat you can do this **without** modifying the WAR file as described
[here](https://tomcat.apache.org/tomcat-7.0-doc/config/context.html#Context_Parameters).

```xml
<Context>
  <Parameter name="key" value="my_secret_key"/>
</Context>
```

Configuration
-------------

To configure the sample payment plugin, navigate to **Manage** > **System
Administration Manager** > **System Settings** > **System Configuration** in
your NetDimensions Learning site, and examine the **Online payment** category.

**Payment plugin URL** should be set to the base URL of the payment plugin
application, followed by `/p/`, followed by the base URL of the NetDimensions
Learning site. For example, if the payment plugin in deployed at
`https://pay.example.com/`, and the NetDimensions Learning site is deployed at
`https://www.example.com/ekp/`, then **Payment plugin URL** should be set to
`https://pay.example.com/p/https://www.example.com/ekp/`.

**Payment plugin key** should be set to the value of
`HMAC_SHA256(master_key, url)`, where `url` is the base URL of the NetDimensions
Learning site. For example, if the master key is `my_secret_key` and the
NetDimensions Learning site is deployed at `https://www.example.com/ekp/`, then
**Payment plugin key** should be set to
`461edb3441708ea098a608d2ebe80527127e6524000fefd92dae5607691cfcf8`. 

Note that the above properties will not appear if your NetDimensions Learning
site has been configured to use one of the natively-supported payment
gateways. To enable payment plugin support, delete or comment out any properties
named either `payment.adapter` or `external_payment.adapter` in your
installation's `WEB-INF/conf/ekp.properties` file.

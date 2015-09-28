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
[here](https://www.dropbox.com/s/1eb9zkz77ctwb3x/sample-payment-plugin.war?dl=0),
or you can build from source using Maven.

You can deploy the WAR file to your application server in the usual way. For
example, if you are using Apache Tomcat, you can copy the WAR file to Tomcat's
`webapps` directory.

You will need to configure two context parameters as follows.

* `merchantUrl` will need to point to the base URL of your NetDimensions Talent
  Suite installation.
* `merchantKey` will need to contain the secret key value used to compute the
  HMAC-MD5 message authentication code for the response.
  
On Tomcat you can do this **without** modifying the WAR file as described
[here](https://tomcat.apache.org/tomcat-7.0-doc/config/context.html#Context_Parameters).

```xml
<Context>
  <Parameter name="merchantUrl" value="https://www.example.com/ekp/"/>
  <Parameter name="merchantKey" value="my_secret_key"/>
</Context>
```

Configuration
-------------

To configure the sample payment plugin, navigate to **Manage** > **System
Administration Manager** > **System Settings** > **System Configuration** in
your NetDimensions Learning site, and examine the **Online payment** category.

**Payment plugin URL** should be set to point to `index.jsp` in your deployment
of the sample payment plugin. For example, if your application server is running
on `https://www.example.com/` and the content path for the web application is
`/samplepaymentplugin`, then **Payment plugin URL** should be set to
`https://www.example.com/samplepaymentplugin/index.jsp`.

**Payment plugin key** should be set to the same value as the `merchantKey`
context parameter mentioned above.

Note that the above properties will not appear if your NetDimensions Learning
site has been configured to use one of the natively-supported payment
gateways. To enable payment plugin support, delete or comment out any properties
named either `payment.adapter` or `external_payment.adapter` in your
installation's `WEB-INF/conf/ekp.properties` file.

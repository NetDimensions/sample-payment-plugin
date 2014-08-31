Sample Payment Plugin
=====================

This project provides a sample payment plugin for use with [NetDimensions Talent
Suite](http://www.netdimensions.com/talent-suite/learning.php), implemented
using JavaServer Pages. Note that this code is intended primarily to demonstrate
the protocol by which the plugin communicates with NetDimensions Talent Suite.
It does **not** validate payment information against a real payment gateway; nor
does it collect payment information from the user.

You may adapt this code to create your own payment plugin for NetDimensions
Talent Suite.

Deployment
----------

The contents of the `src/main/webapp` directory constitute an exploded WAR file,
which you can deploy to your application server in the usual way.

For example, if you are using Apache Tomcat, you can copy the contents of this
directory to a directory named, say, `samplepaymentplugin` under your
installation's `webapps` directory.

You will need to modify the values of two context parameters in
`WEB-INF/web.xml` as follows:

* `com.netdimensions.client.servlet.TALENT_SUITE_BASE_URL` will need to point to
  the base URL of your NetDimensions Talent Suite installation; and
* `com.netdimensions.client.servlet.PAYMENT_PLUGIN_KEY` will need to contain the
  secret key value used to compute the HMAC-MD5 message authentication code for
  the response.

Configuration
-------------

To configure the sample payment plugin, navigate to *Manage* > *System
Administration Manager* > *System Settings* > *System Configuration* in your
NetDimensions Talent Suite site, and examine the *Online payment* category.

*Payment plugin URL* should be set to point to `paymentrequesthandler.jsp` in
your deployment of the sample payment plugin. For example, if your application
server is running on `https://www.example.com/` and the content path for the web
application is `/samplepaymentplugin`, then *Payment plugin URL* should be set
to `https://www.example.com/samplepaymentplugin/payentrequesthandler.jsp`.

*Payment plugin key* should be set to the same value as the
`com.netdimensions.client.servlet.PAYMENT_PLUGIN_KEY` context parameter
mentioned above.

Note that the above properties will not appear if your NetDimensions Talent
Suite site has been configured to use one of the natively-supported payment
gateways. To enable payment plugin support, delete or comment out any properties
named either `payment.adapter` or `external_payment.adapter` in your
installation's `WEB-INF/conf/ekp.properties` file.

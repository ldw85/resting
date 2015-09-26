When we invoke a web service, often the end goal is to take the HTTP response and transform it into value objects which will be rendered in the application. Resting can be used to achieve exactly that.

Resting, a lightweight REST framework in Java, can be used to invoke a RESTful web service and convert the response into custom Java objects from the client application.
Because of it's simplicity, resting is suitable for Android and other handheld devices.

**Goals of resting**

  * Expose simple `get(), post(), put() and delete()` methods to consume REST services
  * Support for all the commonly used MIME types like JSON, XML, ATOM and YAML
  * Enable both HTTP and HTTPS (SSL) invocation of RESTful web services
  * Support for basic authentication
  * Support for proxy
  * Support for arbitrarily complex marshalling and unmarshalling of data during transformation
  * Support for custom representation of collections in REST request
  * Lightweight, simple and fast. Ideal for Android.

**Two Minute Tutorials**

  * A quick tutorial on resting is posted at <a href='http://code.google.com/p/resting/wiki/TwoMinuteTutorial'>Two Minute Tutorial</a>
  * <a href='https://code.google.com/p/resting/wiki/Userguide'>How to use resting with ATOM</a>
  * Use `RestingBuilder` to keep your code minimal and clean. **Highly recommended!!!** A quick tutorial is posted at <a href='http://code.google.com/p/resting/wiki/RestingBuilder'>Tutorial on RestingBuilder</a>

**One Minute Tutorials**

  * <a href='http://code.google.com/p/resting/wiki/ProxyTutorial'>Proxy in resting</a>
  * <a href='http://code.google.com/p/resting/wiki/BasicAuthTutorial'>Basic authentication in resting</a>
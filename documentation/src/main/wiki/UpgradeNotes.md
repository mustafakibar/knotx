# Upgrade Notes
If you are upgrading Knot.x from the previous version, here are notes that will help you do all necessary config
and snippets changes that were introduced in comparison to previous released Knot.x version. If you are upgrading
from older than one version (e.g. 1.0.1 -> 1.1.2) be sure, to do all the steps from the Upgrade Notes of all released
versions. You may see all changes in the [Changelog](https://github.com/Cognifide/knotx/blob/master/CHANGELOG.md).

## Master

## Version 1.3.0
List of changes that are finished but not yet released in any final version.
 - [PR-376](https://github.com/Cognifide/knotx/pull/376) and [PR-397](https://github.com/Cognifide/knotx/pull/397) - Configuration changes:
   - Multiple configuration files format is supported (with favouring the [HOCON](https://github.com/lightbend/config/blob/master/HOCON.md) and supporting nested configurations (includes). **Don't worry, your old `JSON` configurations are still supported!**
   - Removed module descriptors - all module configuration is now defined in the `conf` (written in [HOCON](https://github.com/lightbend/config/blob/master/HOCON.md)) files with proper comments and explanations.
   - Alias for each module can be now created and then used in order to supply module configuration and deploy a single module multiple times under different aliases, now modules are defined like this:
   ```hocon
    modules = [
      "server=io.knotx.server.KnotxServerVerticle"
      "httpRepo=io.knotx.repository.http.HttpRepositoryConnectorVerticle"
      "splitter=io.knotx.splitter.FragmentSplitterVerticle"
      "assembler=io.knotx.assembler.FragmentAssemblerVerticle"
      "hbsKnot=io.knotx.knot.templating.HandlebarsKnotVerticle"
      "serviceKnot=io.knotx.knot.service.ServiceKnotVerticle"
    ]
    ```
   - `java`: prefix is now a default one - don't need to specify it
   - Configurations structure changes:
      - structure of `customFlow` and `defaultFlow` `routing` changed a bit (see the config [cheetsheet](https://github.com/Cognifide/knotx/blob/master/documentation/src/main/cheatsheet/cheatsheets.adoc) for details)
      - `customRequestHeader` property in the Service Adapter renamed to `customHttpHeader`
   - Knot.x instance auto-redeploy itself after the configuration is changed.
   - Get rid of configuration override through system properties - only specific verticles can implement it by themselves if required (currently server only)
 - [PR-399](https://github.com/Cognifide/knotx/pull/399) - `knotx-core` now contains all base Knot.x 
 concepts, Server, Repositories, Splitter, Assembler and Gateway are now one `knotx-core` module. This
 is just the beginning of the bigger changes to make Knot.x more concise and easier to understand.
 - [PR-405](https://github.com/Cognifide/knotx/pull/405) and [PR-412](https://github.com/Cognifide/knotx/pull/412) - Update of dependencies management in the core project. Switched to BOM style dependencies: [`knotx-dependencies`](https://github.com/Knotx/knotx-dependencies).
 Your custom modules may now just elegantly depend on this module that defines all necessary Knot.x dependencies versions.
 - [PR-406](https://github.com/Cognifide/knotx/pull/406) and [PR-411](https://github.com/Cognifide/knotx/pull/411) - `example` and `standalone` modules were not conceptually a part of Knot.x `core`. 
 They were introduces as a quickstart option but having it in the core repository was misleading. We removed those modules and enabled [`knotx-stack`](https://github.com/Knotx/knotx-stack) to enable even easier and faster setup of Knot.x instance.
 - [PR-419](https://github.com/Cognifide/knotx/pull/419) - Knotx snippets parameters prefix is now customizable, default value is
 still `data-knotx` (to keep compatibility), however expect that in the future it will be empty by default 
 (related to [PR-385](https://github.com/Cognifide/knotx/pull/385)). See more in [[Splitter|Splitter]] docs. 
 Important change - earlier `snippetTagName` was defined directly in Splitter and Assembler configs. 
 Since this PR it will be defined in the `snippetOptions` section, under `tagName` property.
 - [PR-421](https://github.com/Cognifide/knotx/pull/421) - support for system properties injection in HOCON config files. You can now customize your config file to use values from system properties. See [System properties in Config](https://github.com/Cognifide/knotx/wiki/Configuration#system-properties) documentation.


## Version 1.2.1
- [PR-385](https://github.com/Cognifide/knotx/pull/385) - From now on you can define the custom 
`snippetTagName` and use that tag name to define dynamic snippets in the templates (instead of 
default `<script>`). Read more about that chang in [[Splitter|Splitter]] and [[Assembler|Assembler]] docs.

## Version 1.2.0
- [PR-345](https://github.com/Cognifide/knotx/pull/335) - Update to Vert.x 3.5 and RxJava 2. Most notable changes are upgrade of Vert.x and RxJava.
The API is changed, so your custom implementations need to adopt to the latest APIs both of Vert.x and RxJava.
- [PR-320](https://github.com/Cognifide/knotx/pull/320) - Now you can configure the file-uploads folder for POST requests. See [Server options](https://github.com/Cognifide/knotx/wiki/Server#server-options) for details.
- [PR-347](https://github.com/Cognifide/knotx/pull/320) - You can now add [custom response header](https://github.com/Cognifide/knotx/wiki/Server#server-options) to the Server. Also custom header can be added to [repository](https://github.com/Cognifide/knotx/wiki/HttpRepositoryConnector#options) & [service](https://github.com/Cognifide/knotx/wiki/HttpServiceAdapter#how-to-configure) requests.
- [PR-349](https://github.com/Cognifide/knotx/pull/349) - You can pass [`Delivery Options`](http://vertx.io/docs/apidocs/io/vertx/core/eventbus/DeliveryOptions.html) to Knot.x Verticles e.g. to manipulate eventbus response timeouts. 
For more details see documentation sections in [[Server|Server#vertx-event-bus-delivery-options]], [[Action Knot|ActionKnot#vertx-event-bus-delivery-options]] and [[Service Knot|ServiceKnot#vertx-event-bus-delivery-options]].
- [PR-354](https://github.com/Cognifide/knotx/pull/354) - You can now enable XSRF protection on default or custom flow at any path. See [[Server|Server#how-to-enable-csrf-token-generation-and-validation]] for details.
- [PR-359](https://github.com/Cognifide/knotx/pull/359) - From now on Knot.x responses with the response of a repository in case of response with status code different than 200. 
 You may play with [`displayExceptionDetails`](https://github.com/Cognifide/knotx/wiki/Server#server-options) flag
 in order to receive errors stack traces.
- [PR-369](https://github.com/Cognifide/knotx/pull/369) - Better support for SSL for Repository Connector. Please check the documentation of [[HttpRepositoryConnector|HttpRepositoryConnector#how-to-configure-ssl-connection-to-the-repository]] for details of how to setup SSL connection.
- [PR-372](https://github.com/Cognifide/knotx/pull/372) - Added cache for compiled Handlebars snippets, you may configure it in Handlebars config, see more in [[Handlebars Knot docs|HandlebarsKnot#how-to-configure]].
- [PR-374](https://github.com/Cognifide/knotx/pull/374) - Enable keepAlive connection in http client options. This is important fix and we recommend to update your existing configuration of any http client and enable `keepAlive` option.
- [PR-379](https://github.com/Cognifide/knotx/pull/379) - Added access logging capabilities to the Knotx HTTP Server. Establish standard configuration of logback logger. Check [[Configure Access Log|Server#configure-access-log]] to see how to configure access logs, and [[Logging|Logging]] to see how loggers can be configured to log to console, files for knot.x, access & netty logs.

## Version 1.1.2
- [PR-335](https://github.com/Cognifide/knotx/pull/335) - Added support for HttpServerOptions on the configuration level.
  * **Important**: The biggest change here is the way port of [[Knot.x Server|Server#vertx-http-server-configurations]] is configured. 
  Previously it was defined in the `config.httpPort` property. Now `serverOptions` section was introduced, see  
  [Vert.x DataObjects page](http://vertx.io/docs/vertx-core/dataobjects.html#HttpServerOptions) for more details. 
  See example change of configuration
  [here](https://github.com/Cognifide/knotx/pull/335/files#diff-9eb56f60d7dcc72e56694b1a0aeb014dL5).

## Version 1.1.1
 - [PR-307](https://github.com/Cognifide/knotx/pull/307) - Fixed KnotxServer default configuration
  * Now default configuration of [[Knot.x Server|Server]] will accept all GET requests.

## Version 1.1.0
- [PR-296](https://github.com/Cognifide/knotx/pull/296) - Support for params on markup and config
  * You may now pass additional `queryParams` via `params` configuration in Service Adapter section.
- [PR-299](https://github.com/Cognifide/knotx/pull/299) - Customize request routing outside knots
  * **Important**: structure of [[Knot.x Server|Server]] configuration has changed because [[Gateway Mode|GatewayMode]] was introduced.
    Now, to configure custom `repositories`, `splitter`, `assembler` and `routing` move all those sections to `defaultFlow`.
    See the example change [here](https://github.com/Cognifide/knotx/pull/299/files#diff-d4c26ef67612264e462c7e4a882023cdL38).
    Additionally, new configuration section (which is not mandatory) `customFlow` was introduced. Read more about it 
    in [[Gateway Mode|GatewayMode]] docs.
- [PR-306](https://github.com/Cognifide/knotx/pull/306) - Additional parameters to adapter from template. 
  * You may pass additional parameters to Action Adapters using `data-knotx-adapter-params`. Read more in [[Action Knot|ActionKnot#example]] docs.

## Version 1.0.1
- [PR-290](https://github.com/Cognifide/knotx/pull/290) - allow defining services without default `params` configured
  * You no longer have to define empty `params` value in `ServiceKnot` config if you don't use any.

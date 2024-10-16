.. _kotlin-upgrade-driver:

=======================
Upgrade Driver Versions
=======================

.. default-domain:: mongodb

.. contents:: On this page
   :local:
   :backlinks: none
   :depth: 1
   :class: singlecol


Overview
--------

In this section, you can identify the changes you need to make to your
application to upgrade your driver to a new version.

Before you upgrade, perform the following actions:

- Ensure the new version is compatible with the {+mdb-server+} versions
  your application connects to and the Java Runtime Environment (JRE) your
  application runs on. See the :ref:`Java Compatibility <kotlin-compatibility-tables>`
  page for this information.
- Address any breaking changes between the current version of the driver
  your application is using and your planned upgrade version in the
  :ref:`Breaking Changes <kotlin-breaking-changes>` section. To learn
  more about the {+mdb-server+} release compatibility changes, see the
  :ref:`<kotlin-server-release-changes>` section.

.. tip::

   To minimize the amount of changes your application may require when
   upgrading driver versions in the future, use the
   :ref:`{+stable-api+} <stable-api-kotlin>`.

.. _kotlin-breaking-changes:

Breaking Changes
----------------

A breaking change is a modification in a convention or behavior in
a specific version of the driver that may prevent your application from
working properly if not addressed before upgrading.

The breaking changes in this section are categorized by the driver version that
introduced them. When upgrading driver versions, address all the breaking
changes between the current and upgrade versions. For example, if you
are upgrading the driver from v4.0 to v4.5, address all breaking changes from
the version after v4.0 including any listed under v4.5.

.. _kotlin-breaking-changes-v4.8:

Version 4.8 Breaking Changes
~~~~~~~~~~~~~~~~~~~~~~~~~~~~

- The driver ends support for connecting to {+mdb-server+} versions v3.4 and
  earlier. To learn more about this change, see the :ref:`<kotlin-server-release-change-v4.8>`
  section.
- You must add an explicit dependency on the ``org.bson.codecs.record``
  module if your application deploys the driver in an OSGi container and
  relies on the driver for encoding and decoding Java records.

- The ``RecordCodec``, implemented in v4.6, deserialized POJOs and record
  classes that are specified as type parameters of ``List`` or ``Map`` fields
  of a record as ``Document`` values instead of their respective classes. This
  version now deserializes them to the proper record and POJO types.

  For example, the following record class definitions show a ``Book`` record
  that contains a ``List`` that receives a ``Chapter`` type parameter:

  .. code-block:: java

     public record Book(String title, List<Chapter> chapters) {}
     public record Chapter(Integer number, String text) {}

  Starting in this version, the codec deserializes data in the ``List`` into
  ``Chapter`` record classes instead of ``Document`` values.

.. _kotlin-breaking-changes-v4.7:

Version 4.7 Breaking Changes
~~~~~~~~~~~~~~~~~~~~~~~~~~~~

- The ``setWindowFields`` builder API is no longer beta. The new builder
  breaks binary and source compatibility. See the
  `Aggregates API documentation <https://mongodb.github.io/mongo-java-driver/4.7/apidocs/mongodb-driver-core/com/mongodb/client/model/Aggregates.html>`__
  for information the new ``setWindowFields()`` method signatures.

  If your application uses this builder in a version prior to v4.7, update
  your source code to use the new method signature and rebuild your binary.

.. _kotlin-breaking-changes-v4.2:

Version 4.2 Breaking Changes
~~~~~~~~~~~~~~~~~~~~~~~~~~~~

- The ``ObjectId`` class and its ``serialVersionUID`` field were updated
  to use a new format that minimizes serialization compatibility issues
  across different versions of the driver.

  If an application using this version or later of the driver attempts to
  perform Java Object Serialization on any objects that contain an
  ``ObjectId`` and were serialized by a prior version of the driver, Java
  throws an ``InvalidClassException``.

  To learn more about Java Object Serialization, see the Java
  Documentation on `Serializable Objects <https://docs.oracle.com/javase/tutorial/jndi/objects/serial.html>`__.

.. _kotlin-breaking-changes-v4.0:

Version 4.0 Breaking Changes
~~~~~~~~~~~~~~~~~~~~~~~~~~~~

- Several classes and methods marked as deprecated in the 3.12 release
  were removed in this version.
- The insert helper methods return an insert result object instead of
  ``void``.
- The ``toJson()`` methods on ``BsonDocument``, ``Document``, and
  ``DbObject`` return a relaxed JSON format instead of a strict JSON
  format. This makes the JSON documents more readable, but can make it more
  difficult to identify the BSON type information, such as the difference
  between a 32-bit and 64-bit integer. If your application relies on the
  strict JSON format, use the strict mode when reading or writing data.
  Learn how to specify the JSON format in the current API in the
  :ref:`Document Data Format: Extended JSON <kotlin-extended-json>` guide.
- The default BSON representation of ``java.util.UUID`` value was changed
  from ``JAVA_LEGACY`` to ``UNSPECIFIED``. Applications that store or retrieve
  UUID values must explicitly specify which representation to use. You can
  specify the representation in the ``uuidRepresentation`` property of
  ``MongoClientSettings``.
- The connection pool no longer restricts the number of wait queue threads
  or asynchronous tasks that require a connection to MongoDB. The
  application should throttle requests as necessary rather than depend on
  the driver to throw a ``MongoWaitQueueFullException``.
- The driver no longer logs using the ``java.util.logging`` (JUL) package
  and only supports the SLF4J logging framework.
- The embedded and Android drivers were removed. If your application
  relies on these drivers, you must continue to use a 3.x Java driver
  version.
- The uber JARs, ``mongo-java-driver`` and ``mongodb-driver``, are no
  longer published. If your application relies on one of these, you must
  switch to either ``mongodb-driver-sync`` or ``mongodb-driver-legacy``
  depending on which API the application uses. Make sure you remove the
  uber JARs from your dependencies.
- Updates to several classes introduced binary compatibility breaks, such
  as the method signature change to the insert helper methods. Recompile
  any classes that link to the driver against this version or later to ensure
  that they continue to work.

.. _kotlin-server-release-changes:

Server Release Compatibility Changes
------------------------------------

A server release compatibility change is a modification
to the {+driver-long+} that discontinues support for a set of
{+mdb-server+} versions.

The driver discontinues support for a {+mdb-server+} version after it reaches
end-of-life (EOL).

To learn more about the MongoDB support for EOL products,
see the `Legacy Support Policy <https://www.mongodb.com/support-policy/legacy>`__.

.. _kotlin-server-release-change-v4.8:

Version 4.8 Server Release Support Changes
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

- The v4.8 driver drops support for {+mdb-server+} v3.4 and earlier.
  To use the v4.8 driver, your {+mdb-server+} must be v3.6 or later. To learn
  how to upgrade your {+mdb-server+} to v3.6, follow the link that corresponds
  to your MongoDB deployment configuration:

  - :ref:`<3.6-upgrade-replica-set>`
  - :ref:`<3.6-upgrade-standalone>`
  - :ref:`<3.6-upgrade-sharded-cluster>`

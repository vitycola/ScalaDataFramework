package org.framework.exception

object CommonException {

  final case class DataSourceNotImplementedError(
      private val message: String = "Data Source not defined",
      private val cause: Throwable = None.orNull)
    extends Exception(message, cause)

  final case class HostNotDefinedError(
      private val message: String = "Data Source host must be defined in conf",
      private val cause: Throwable = None.orNull)
    extends Exception(message, cause)

}

package com.interview

import org.apache.spark
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{BooleanType, DoubleType, IntegerType, StringType, StructType}
import org.apache.spark.{SparkConf, SparkContext}

object stockexchange {

  case class schema(OrderID: Int, UserName: String, OrderTime: String, OrderType: String, Quantity: Int, Price: Int)

  def main(args: Array[String]): Unit = {
    try{
      val conf = new SparkConf().setMaster("local").setAppName("usdata")
      val sc = new SparkContext(conf)
      sc.setLogLevel("error")

      val spark = SparkSession.builder().getOrCreate()

      val inputOrdersFilePath = args(0)
      val inputOrdersFileName = args(1)

      val schema = new StructType()
        .add("OrderID", IntegerType, true)
        .add("UserName", StringType, true)
        .add("OrderTime", StringType, true)
        .add("OrderType", StringType, true)
        .add("Quantity", IntegerType, true)
        .add("Price", IntegerType, true)

      val df = spark.read.format("csv")
        .option("header", "true")
        .schema(schema)
        .option("inferschema", "true")
        .load(inputOrdersFilePath+"/"+inputOrdersFileName)

      df.printSchema()
      df.show(false)

      var order_book = Map.empty[Int, (Int, String, String, String, Int, Int)]

      for (row <- df.collect()) {

        val quantity = row.getAs[Int]("Quantity")
        val orderType = row.getAs[String]("OrderType")
        val orderId = row.getAs[Int]("OrderID")
        val orderTime = row.getAs[String]("OrderTime")

        if (order_book.contains(quantity) && order_book(quantity)._4 != orderType) {

          val matched = order_book(quantity)

          println(s"matched Orders: ${matched._1} ${orderId} ${orderTime} ${matched._5} ${matched._6}")

        } else {

          order_book += (quantity -> (orderId, row.getAs[String]("UserName"), orderTime, orderType, quantity, row.getAs[Int]("Price")))

        }
      }
    }catch{

      case e:Throwable=>e.printStackTrace()

    }
  }
}

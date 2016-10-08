package model

import slick.driver.PostgresDriver.api._

case class Restaurant(name:String,city:String,address:String,phone:Long)
case class CompleteRestaurant(id:Long,name:String,city:String,address:String,phone:Long)

class RestaurantTableDef(tag:Tag) extends Table[CompleteRestaurant](tag,"restaurant"){

  def id=column[Long]("id",O.PrimaryKey,O.AutoInc)
  def name=column[String]("name")
  def city=column[String]("city")
  def address=column[String]("address")
  def phone=column[Long]("phone")
  override def * = (id,name,city,address,phone)<>(CompleteRestaurant.tupled,CompleteRestaurant.unapply)
}
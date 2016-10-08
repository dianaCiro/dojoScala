package dao
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import play.api.Play
import model.RestaurantTableDef
import scala.concurrent.Future
import slick.driver.PostgresDriver.api._
import scala.concurrent.ExecutionContext.Implicits.global
import model.CompleteRestaurant


//tipo object para que la clase sea singleton
object Restaurants {
  val dbConfig=DatabaseConfigProvider.get[JdbcProfile](Play.current)
  //query que nos va a permitir manejar las consultas
  val restaurants=TableQuery[RestaurantTableDef]
  //poder manejar consultas en paralelo
  //se retorna una lsita o secuencia de restaurtantes
  def list:Future[Seq[CompleteRestaurant]]={
    dbConfig.db.run(restaurants.result)
  }
  //metodo para obtener un restaurante ingresando el id
  //option:puede o no retornar nada
  //headOption:toma el primer elemento de la lista, si no arroja resultados el maneja ale exception
  def getById(id:Long):Future[Option[CompleteRestaurant]]={
    dbConfig.db.run(restaurants.filter(_.id===id).result.headOption)//hacerle un filtro por id a la tabla restaurant
  }
   
   def save(restaurant:CompleteRestaurant):Future[String]={
    //permite guardar en la base de datos, simplemente diciendo la tabla += el elemento que estamos insertando
    dbConfig.db.run(restaurants+=restaurant).map(res => "Restaurant saved").recover{
      //retornar el mensaje de lo que fallo
      case ex: Exception => ex.getCause.getMessage
    }
  }
   
   def update(restaurant:CompleteRestaurant):Future[Int]={
    dbConfig.db.run(restaurants.filter(_.id===restaurant.id).update(restaurant))
  }
   
    def deleteById(id:Long):Future[Int]={
    dbConfig.db.run(restaurants.filter(_.id===id).delete)//hacerle un filtro por id a la tabla restaurant
  }
    
  }
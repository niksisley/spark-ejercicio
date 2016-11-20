package org.dummy

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._


object ScalaApp extends App {
  val logFile = "OlympicAthletes.csv"
  val sc = new SparkContext("local", "Simple", "$SPARK_HOME"
    , List("target/spark-ejercicio-1.0.jar"))
  val file = sc.textFile(logFile)

  val olympicMedalRecordsRDD = file.map(x => {
    val arr = x.split(",")
    new OlympicMedalRecords(arr(0), Integer.parseInt(arr(1)), arr(2)
      , Integer.parseInt(arr(3)), arr(5), Integer.parseInt(arr(6)),
      Integer.parseInt(arr(7)), Integer.parseInt(arr(8)))
  }
  )

  /* Haz un proceso que compare las medallas que han ganado China y EEUU en
todos los años registrados en el fichero. */


  val olympicAthletesRDD = olympicMedalRecordsRDD.map(x => (x.getOlympicGame, x)).groupByKey()
    .map(x => {
      val olympicGame = x._1
      val medallByUSA = x._2.filter(x => x.getCountry == "United States").map(y => y.getGoldMedals + y.getSilverMedals + y.getBronzeMedals)
        .sum
      val medallByChina = x._2.filter(x => x.getCountry == "China").map(y => y.getGoldMedals + y.getSilverMedals + y.getBronzeMedals)
        .sum
      val diferencia = medallByUSA - medallByChina
      new OlympicAthletes(olympicGame,medallByUSA,medallByChina,diferencia)
    }).foreach(x=>println(x.getolympicGame + "--" + x.getmedallByUSA + "--" + x.getmedallByChina + "--" + x.getdiferencia))

  /* Dando puntos a cada una de las medallas (3 puntos oro, 2 puntos plata y 1
bronce) calcula el máximo de puntos que ha conseguido en todos los juegos olímpicos que ha participado. */


  val olympicCountriesRDD = olympicMedalRecordsRDD.map(x => (x.getCountry, x)).groupByKey()
    .map(x => {
      val country = x._1
      val medalByYearold = x._2.groupBy(y => y.getOlympicGame).map(y =>
        (y._1, y._2.map(y => y.getGoldMedals * 3 + y.getSilverMedals * 2 + y.getBronzeMedals)
          .sum)).max
      val medalByYear = medalByYearold._2
      val Year = medalByYearold._1
      new OlympicCountries(country, medalByYear, Year)
    }).foreach(x => println(x.getcountry + "---" + x.getmedalByYear + "---" + x.getYear))

  /*Recupera a los tres mejores medallistas olímpicos (por numero de medallas) en
cada una de los juegos olímpicos. */

  val MedallsAthletesAño = olympicMedalRecordsRDD.map(m => m.getOlympicGame -> (m.getName, m.getGoldMedals + m.getSilverMedals + m.getBronzeMedals)).groupByKey()
    .sortBy(m => m._2).mapValues(m => m.take(3)).foreach(m => println(m))

}
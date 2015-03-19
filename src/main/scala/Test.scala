import java.io.{File, PrintWriter, BufferedInputStream, FileInputStream}
import java.nio.file.{Files, Paths, StandardOpenOption}
import java.util.zip.GZIPInputStream

import sun.nio.cs.StandardCharsets

import scala.io.Source

/**
 * Created by Avils on 2015-02-11.
 */

object Test {
  def main(args: Array[String]) {
    val csvFileName = "palnes_log.csv.gz"

    println("I am in " + System.getProperty("user.dir"))
    val reader = loadZippedCsv(csvFileName).getLines().toList.drop(1)
//    val reader = loadCsv("example.csv").getLines().toList.drop(1)

    val records = reader.map(s => new Record(s.split(",")))

//    writeFile("1 airports-arrived planes by month.txt", flightsByDestination(records))
    writeFile("1 airports-arrived planes per month.txt", flightsByDestPerMonth(records))
    writeFile("2 diff between arrived and left.txt", diffBetweenReceivedAndLeaved(records))


  }
  def loadZippedCsv(s: String) = Source.fromInputStream(new GZIPInputStream(new BufferedInputStream(new FileInputStream(s))))
  def loadCsv(s: String) = Source.fromInputStream(new BufferedInputStream(new FileInputStream(s)))

  def writeFile(name: String, content: String ) = {
    val writer = new PrintWriter(new File(name))

    writer.write(content)
    writer.close()


  }


  //1.a File with list all airports and number of planes arrived to each of them during month = all
  def flightsByDestination(records: List[Record]) = {

    var result = new StringBuilder
    val byDest = records.groupBy(_.dest)
    for(dest <- byDest){
      printf("\nairport: %s flights %s", dest._1, dest._2.size)
      result.append(String.format("airport: %s flights %s\n", dest._1, dest._2.size.toString) )
    }
    result.toString()
  }

  def flightsByDestPerMonth(records: List[Record]) = {
    var result = new StringBuilder
    val byDest = records.groupBy(_.dest)

    for(dests <- byDest){
      printf("airport: %s total flights %s\n", dests._1, dests._2.size)
      result.append(String.format("airport: %s flights %s\n", dests._1, dests._2.size.toString))
      val destByMonth = dests._2.groupBy(_.month)

      for(months <- destByMonth){
        printf("\n\t| month %s, number of total flights %s", months._1, months._2.size)
        result.append(String.format("\t| month %s, number of flights %s\n", months._1, months._2.size.toString))
      }
    }

    result.toString()
  }

//  1.b File with list all airports and number of planes arrived to each of them during month = x
  def flightsByDestPerMonth(records: List[Record], month: String) = {
    val byDest = records.groupBy(_.dest)
    for(dests <- byDest){
      val numOfFlights = dests._2.filter(r=> r.month.equals(month))
      printf("\nairport: %s has %s flights in %s month", dests._1, numOfFlights.size, month)

    }
  }

  def diffBetweenReceivedAndLeavedInverted(records: List[Record]) = {
    //origin - left
    //dest - arrived
    var result = new StringBuilder

    val byOrigin = records.groupBy(_.origin)
    for(origin <- byOrigin) {
      val numOfReceivedFlightsByDest = records.filter(dest => dest.dest.equals(origin._1))
      printf("\n airport %s: received %s left %s", origin._1, origin._2.size,
        numOfReceivedFlightsByDest.size)

      val diff = origin._2.size - numOfReceivedFlightsByDest.size
      printf("\n diff %s", diff)

      if (diff != 0) {
//      result.append(String.format("airport %s: diff %s||received %s left %s\n", origin._1.toString, diff.toString,
//        origin._2.size.toString, numOfReceivedFlightsByDest.size.toString))
      result.append(String.format("airport %s: diff %s\n", origin._1.toString, diff.toString))
      }
    }

    result.toString()

  }

  def diffBetweenReceivedAndLeaved(records: List[Record]) = {
    //origin - left
    //dest - arrived
    var result = new StringBuilder

    val byArrive = records.groupBy(_.dest)       //right
    for(arriveAirport <- byArrive) {             //for every right
      val numOfLeftPlanesByOrigin = records.filter(origin => origin.origin.equals(arriveAirport._1)) //count how many left
      printf("\n airport %s: got %s left %s", arriveAirport._1, arriveAirport._2.size, numOfLeftPlanesByOrigin.size)

      val diff = arriveAirport._2.size - numOfLeftPlanesByOrigin.size
      printf("\n diff %s", diff)

      if (diff != 0) {
        //      result.append(String.format("airport %s: diff %s||received %s left %s\n", arriveAirport._1.toString, diff.toString,
        //        arriveAirport._2.size.toString, numOfLeftPlanesByOrigin.size.toString))
        result.append(String.format("airport %s: diff %s\n", arriveAirport._1.toString, diff.toString))
      }
    }

    result.toString()

  }
}
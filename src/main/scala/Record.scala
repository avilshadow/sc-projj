/**
 * Created by olegm on 12/02/2015.
 */
class Record(arr: Array[String]) {
  val year = arr(0)
  val quarter = arr(1)
  val month = arr(2)
  val dayOfMonth = arr(3)
  val dayOfWeek = arr(4)
  val flDate = arr(5)
  val origin = arr(6).replaceAll("\"","")
  val dest = arr(7).replaceAll("\"","")

  def getYear = year
  def getQuarter = quarter


  def print() = printf("%s %s %s %s %s %s %s %s", year, quarter, month, dayOfMonth, dayOfWeek, flDate, origin, dest)
  def str() = String.format("%s %s %s %s %s %s %s %s", year, quarter, month, dayOfMonth, dayOfWeek, flDate, origin, dest)
}

/*
"YEAR","QUARTER","MONTH","DAY_OF_MONTH","DAY_OF_WEEK","FL_DATE",    "ORIGIN","DEST",
2014,   1,       1,       1,            3,            2014-01-01,   "JFK",  "LAX",*/

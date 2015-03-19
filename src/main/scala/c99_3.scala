// P03 (*) Find the Kth element of a list.
//     By convention, the first element in the list is element 0.
//
//     Example:
//     scala> nth(2, List(1, 1, 2, 3, 5, 8))
//     res0: Int = 2
//    http://stackoverflow.com/questions/29094632/scala-pattern-matching-enigma

object P03 {

  def main(args: Array[String]): Unit = {

    val li = List(1, 1, 2, 3, 5, 8)
    val li1 = List(1, 1, 2, 3, 2, 1, 1)
    val li2 = List(1, 1, 3, 3, 1, 1)

//    println(nthRecursive(4, li))
//    println(nthRecursive(3, li))
//    println(nthRecursive(5, li))

//    println(isPalindrome(li), isPalindrome2(li))
//    println(isPalindrome(li1), isPalindrome2(li1))
//    println(isPalindrome(li2), isPalindrome2(li2))

    val fl = List(List(1, 1), 2, List(3, List(5, 8)))
    println(fl, "=>", flatten(fl))


  }

  // Trivial with builtins.
  def nthBuiltin[A](n: Int, ls: List[A]): A =
    if (n >= 0) ls(n)
    else throw new NoSuchElementException

  // Not that much harder without.
  def nthRecursive[A](n: Int, ls: List[A]): A = (n, ls) match {
    case (0, h :: _   ) => h
    case (n, _ :: tail) => nthRecursive(n - 1, tail)
    case (_, Nil      ) => throw new NoSuchElementException
  }

  def isPalindrome[A](ls: List[A]): Boolean = ls == ls.reverse

  def isPalindrome2[A](ls: List[A]): Boolean = {
    if (ls.size % 2 == 0 )      ls.slice(0,ls.size/2) == ls.slice(ls.size/2, ls.size).reverse
    else        ls.slice(0,ls.size/2+1) == ls.slice(ls.size/2, ls.size).reverse
  }

  def flatten(ls: List[Any]): List[Any] = ls flatMap {
    case ms: List[_] => flatten(ms)
    case e => List(e)
  }
}
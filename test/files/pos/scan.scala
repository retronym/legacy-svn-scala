



object Test {

  def main(args: Array[String]) {
    val lst = List(1, 2, 3, 4, 5)
    
    assert(lst.scanLeft(0)(_ + _) == List(0, 1, 3, 6, 10, 15))
    assert(lst.scanRight(0)(_ + _) == List(0, 5, 9, 12, 14, 15))
    
    val emp = List[Int]()
    assert(emp.scanLeft(0)(_ + _) == List(0))
    assert(emp.scanRight(0)(_ + _) == List(0))
  }

}
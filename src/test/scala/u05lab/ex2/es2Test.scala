package u05lab.ex2

import org.junit.Test
import u05lab.ex2.ConferenceReviewImpl
import u05lab.ex2.Question.*
import org.junit.Assert.*

class es2Test:
  val cr = ConferenceReviewImpl()

  // carico una revisione per l'articolo 1:
  // - 8 per relevance, significance e final
  // - 7 per confidence
  // si ricordi che l'ordine delle domande è: relevance, significance, confidence, final
  cr.loadReview(1, 8, 8, 6, 8) // 4.8 è il voto finale pesato (usato da averageWeightedFinalScoreMap)
  // e simile per gli altri
  cr.loadReview(1, 9, 9, 6, 9) // 5.4
  cr.loadReview(2, 9, 9, 10, 9) // 9.0
  cr.loadReview(2, 4, 6, 10, 6) // 6.0
  cr.loadReview(3, 3, 3, 3, 3) // 0.9
  cr.loadReview(3, 4, 4, 4, 4) // 1.6
  cr.loadReview(4, 6, 6, 6, 6) // 3.6
  cr.loadReview(4, 7, 7, 8, 7) // 5.6
  val map: Map[Question, Int] = Map(RELEVANCE -> 8, SIGNIFICANCE -> 8, CONFIDENCE-> 7, FINAL -> 8)
  cr.loadReview(4,map)
  cr.loadReview(5, 6, 6, 6, 10) // 6.0
  cr.loadReview(5, 7, 7, 7, 10) // 7.0

  @Test
  def testLoadReview(): Unit =
    val cr = ConferenceReviewImpl()
    cr.loadReview(1, 8, 8, 6, 8)
    assertEquals(List((1, Map(RELEVANCE -> 8, SIGNIFICANCE -> 8, CONFIDENCE-> 6, FINAL -> 8))), cr.getReviews)

  @Test
  def testLoadReview2(): Unit =
    val cr = ConferenceReviewImpl()
    cr.loadReview(1, Map(RELEVANCE -> 8, SIGNIFICANCE -> 8, CONFIDENCE-> 6, FINAL -> 8))
    assertEquals(List((1, Map(RELEVANCE -> 8, SIGNIFICANCE -> 8, CONFIDENCE-> 6, FINAL -> 8))), cr.getReviews)

  @Test
    def testOrderedScores(): Unit =
    // l'articolo 2 ha preso su RELEVANCE i due voti 4,9
    assertEquals(List(4, 9), cr.orderedScores(2, Question.RELEVANCE))
    // e simile per gli altri
    assertEquals(List(6,7,8), cr.orderedScores(4, Question.CONFIDENCE))
    assertEquals(List(10,10), cr.orderedScores(5, Question.FINAL))

  @Test
    def testAvarageFinalScore(): Unit =
      // l'articolo 1 ha preso voto medio su FINAL pari a 8.5, con scarto massimo 0.01
      assertEquals(cr.averageFinalScore(1), 8.5, 0.01);
      // e simile per gli altri
      assertEquals(cr.averageFinalScore(2), 7.5, 0.01);
      assertEquals(cr.averageFinalScore(3), 3.5, 0.01);
      assertEquals(cr.averageFinalScore(4), 7.0, 0.01);
      assertEquals(cr.averageFinalScore(5), 10.0, 0.01);

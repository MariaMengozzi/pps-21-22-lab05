package u05lab.ex2

import java.util.stream.Collectors

enum Question:
  case RELEVANCE,       // ("È importante per questa conferenza?"),
  SIGNIFICANCE, 	// ("Produce contributo scientifico?"),
  CONFIDENCE,   	// ("Ti senti competente a commentarlo?");
  FINAL        	// ("É un articolo da accettare?")

trait ConferenceReview:
  def loadReview(article: Int, map: Map[Question, Int]): Unit
  def loadReview(article: Int, r: Int, s: Int, c: Int, f: Int): Unit
  def orderedScores(article: Int, question: Question): List[Int]
  def averageFinalScore(article: Int): Double
  def acceptedArticles: Set[Int]
  def sortedAcceptedArticles : List[(Int, Double)]
  def averageWeightedFinalScore(article: Int): Double
  def averageWeightedFinalScoreMap: Map[Int, Double]


class ConferenceReviewImpl extends ConferenceReview:
  private var reviews = List[(Int, Map[Question, Int])]()
  def getReviews: List[(Int, Map[Question, Int])] = reviews

  override def loadReview(article: Int, map: Map[Question, Int]): Unit = reviews = reviews :+ (article, map)

  override def loadReview(article: Int, r: Int, s: Int, c: Int, f: Int): Unit =
    import Question.*
    val map = Map( RELEVANCE -> r, SIGNIFICANCE -> s, CONFIDENCE-> c, FINAL -> f)
    loadReview(article, map)

  override def orderedScores(article: Int, question: Question): List[Int] =
    reviews.filter(_._1 == article)
      .map(_._2.get(question).get)
      .sorted
      .toList

  override def averageFinalScore(article: Int): Double =
    reviews.filter(_._1 == article)
      .map(_._2.get(Question.FINAL).get)
      .sum / reviews.filter(_._1 == article).length

  override def acceptedArticles: Set[Int] = ???
  override def sortedAcceptedArticles : List[(Int, Double)] = ???
  override def averageWeightedFinalScore(article: Int): Double = ???
  override def averageWeightedFinalScoreMap: Map[Int, Double] = ???

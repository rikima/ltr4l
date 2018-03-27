/*
 * Copyright 2018 org.LTR4L
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ltr4l.evaluation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.ltr4l.query.Document;
import org.ltr4l.query.Query;
import org.ltr4l.trainers.Trainer;

public interface RankEval {
  static int countNumRelDocs(List<Document> docRanks){
    return docRanks.stream().filter(doc -> doc.getLabel() != 0).mapToInt(doc -> 1).sum();
  }

  //TODO: Confirm definition and write test.
  static double cg(List<Document> docRanks, int position){
    double cg = 0;
    int pos = Math.min(position, docRanks.size());
    for (int k = 0; k < pos; k++) cg += Math.pow(2, docRanks.get(k).getLabel()) - 1;
    return cg;
  }

  default double calculateAvgAllQueries(Trainer trainer, List<Query> queries, int position){
    return queries.stream().mapToDouble(query -> calculate(trainer.sortP(query), position)).sum() / queries.size();
/*    double total = 0;
    for (Query query : queries) total += calculate(trainer.sortP(query), position);
    return total / queries.size();*/
  }

  double calculate(List<Document> docRanks, int position);

}
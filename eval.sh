#!/bin/sh

alg=ranknet
dataset=("MQ2007" "MQ2008")
for data in ${dataset[@]} ; do
    for i in $(seq 1 5) ; do
	echo $data Fold$i
	model=./model/$alg-$data-Fold$i-model.json

	# train
	./train $alg -training ./data/$data/Fold$i/train.txt -validation ./data/$data/Fold$i/vali.txt -model $model 
	
	# predict
	./predict $alg -eval ndcg -k 10 -test ./data/$data/Fold$i/test.txt -report ./report/ranknet-$data-Fold$i.csv -model $model
    done
done

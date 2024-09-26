dir=rand_${2}
mkdir $dir

for f in $(eval echo {1..$1}); do
  tr -dc A-Za-z0-9 </dev/urandom | head -c ${2} > ${dir}/${f}_rand.txt
done
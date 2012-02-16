A=bootlog.stock
B=bootlog.2.6.28
sed -e 's/^\[............\]/[            ]/' $A > $A.tmp
sed -e 's/^\[............\]/[            ]/' $B > $B.tmp
diff -u $A.tmp $B.tmp

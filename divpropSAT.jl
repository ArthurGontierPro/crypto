


function fixx(f,x,t)
        if t
	        write(f,string(x," 0\n"))
        else
                write(f,string("-",x," 0\n"))
        end
end
function copy(f,a,b,c,d)
	write(f,string("-",b," 0\n"))
	write(f,string("-",a," ",c," ",d," 0\n"))
	write(f,string(a," -",d," 0\n"))
	write(f,string(a," -",c," 0\n"))
	write(f,string("-",c," -",d," 0\n"))
end
function and(f,a,b,c,d)
	write(f,string(a," -",c," 0\n"))
	write(f,string("-",b," ",d," 0\n"))
	write(f,string("-",d," ",c," 0\n"))
	write(f,string("-",a," ",d," ",c," 0\n"))
        write(f,string(a," ",b," -",d," 0\n"))
end
function xor(f,a,b,c,d)
	write(f,string(a," -",c," 0\n"))
	write(f,string("-",b," ",d," 0\n"))
	write(f,string(b," -",d," -",c," 0\n"))
	write(f,string("-",a," ",c," ",d," 0\n"))
	write(f,string(a," ",b," -",d," 0\n"))
	write(f,string("-",b," -",a," ",c," 0\n"))
end

open("divprop.cnf","w") do f
        copy(f,1,2,3,4)
        and(f,5,6,7,8)
        xor(f,9,10,11,12)
        fixx(f,9,true)
end




#~/z3/build/z3 ~/Desktop/crypto/divprop.cnf

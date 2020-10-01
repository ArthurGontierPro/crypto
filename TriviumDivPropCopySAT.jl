ch="/Users/agontier/Desktop/crypto/"
global Nb = 840
global copy1=[i for i in 288+1:288+5]
global copy2=[i for i in 288+6:288+10]
global copy3=[i for i in 288+11:288+15]
global ends = 288+15
function fixx(f,x,t)
        if t
	        write(f,string(x," 0\n"))
        else
                write(f,string("-",x," 0\n"))
        end
end
function fixx(f,x,t)
        if t
                for xi in x
	                write(f,string(xi," 0\n"))
                end
        else
                for xi in x
                        write(f,string("-",xi," 0\n"))
                end
        end
end
function copyt(f,a,b,c)
	write(f,string("-",a," ",b," ",c," 0\n"))
	write(f,string(a," -",b," 0\n"))
	write(f,string(a," -",c," 0\n"))
end
function and(f,y,b,c)
	write(f,string("-",b," -",c," ",y," 0\n"))
	write(f,string("-",y," ",b," 0\n"))
	write(f,string("-",y," ",c," 0\n"))
end
function xortriv(f,a,y,d,e,t)
	write(f,string(t," -",a," 0\n"))
	write(f,string(t," -",y," 0\n"))
	write(f,string(t," -",d," 0\n"))
	write(f,string(t," -",e," 0\n"))
	write(f,string(a," ",y," ",d," ",e," -",t," 0\n"))
        write(f,string("-",a," -",y," 0\n"))
        write(f,string("-",a," -",d," 0\n"))
        write(f,string("-",a," -",e," 0\n"))
        write(f,string("-",y," -",d," 0\n"))
        write(f,string("-",y," -",e," 0\n"))
        write(f,string("-",d," -",e," 0\n"))
end
function laststate(f)
        r = Nb
        fixx(f,[r*ends+i for i in 1:288 if i != 66 && i != 93 && i != 162 && i != 177 && i != 243 && i != 288],false)
        write(f,string(r*ends+66," ",r*ends+93," ",r*ends+162," ",r*ends+177," ",r*ends+243," ",r*ends+288," 0\n"))
        write(f,string("-",r*ends+66," -",r*ends+93," 0\n"))
        write(f,string("-",r*ends+66," -",r*ends+162," 0\n"))
        write(f,string("-",r*ends+66," -",r*ends+177," 0\n"))
        write(f,string("-",r*ends+66," -",r*ends+243," 0\n"))
        write(f,string("-",r*ends+66," -",r*ends+288," 0\n"))
        write(f,string("-",r*ends+93," -",r*ends+162," 0\n"))
        write(f,string("-",r*ends+93," -",r*ends+177," 0\n"))
        write(f,string("-",r*ends+93," -",r*ends+243," 0\n"))
        write(f,string("-",r*ends+93," -",r*ends+288," 0\n"))
        write(f,string("-",r*ends+162," -",r*ends+177," 0\n"))
        write(f,string("-",r*ends+162," -",r*ends+243," 0\n"))
        write(f,string("-",r*ends+162," -",r*ends+288," 0\n"))
        write(f,string("-",r*ends+177," -",r*ends+243," 0\n"))
        write(f,string("-",r*ends+177," -",r*ends+288," 0\n"))
        write(f,string("-",r*ends+243," -",r*ends+288," 0\n"))
end
function shift(f,s)
        for x in s
                write(f,string(x," -",x-ends-1," 0\n"))
                write(f,string(x-ends-1," -",x," 0\n"))
        end
end
function f(f,r,t,copy)
        for i in [1,2,3,5] # copy of a,b,c,e
                copyt(f,(r-1)*ends+t[i],(r-1)*ends+copy[i],r*ends+t[i]+1)
        end
        and(f,(r-1)*ends+copy[4],(r-1)*ends+copy[2],(r-1)*ends+copy[3])
        xortriv(f,(r-1)*ends+copy[1],(r-1)*ends+copy[4],(r-1)*ends+t[4],(r-1)*ends+copy[5],r*ends+t[6])
end
function generate()
        open(string(ch,"trivium.cnf"),"w") do d
                # constantes à 0
                fixx(d,[i for i in 81:93],false)
                fixx(d,[i for i in 93+81:285],false)
                # cube
                fixx(d,[i for i in 94:93+80 if i!= 93+34 && i!=93+47],true)
                fixx(d,93+34,false)
                fixx(d,93+47,false)
                # Monome clé (test papier 441 p16)
                fixx(d,[i for i in 1:80 if i!= 12],false)
                fixx(d,12,true)
                # Last state
                laststate(d)
                
                tt3 = [243, 286, 287, 288, 69, 1] #a+bc+d+e=t
                tt1 = [66, 91, 92, 93, 171, 94]
                tt2 = [162, 175, 176, 177, 264, 178]
                for r in 1:(Nb)
                        f(d,r,tt3,copy3)
                        f(d,r,tt1,copy1)
                        f(d,r,tt2,copy2)
                        shift(d,[r*ends+j for j in 2:288 if !(j-1 in tt1[1:end-1]) && !(j-1 in tt2[1:end-1]) && !(j-1 in tt3[1:end-1]) && !(j in [1,94,178])]) 
                end
        end
end
function solve()
        run(pipeline(`/Users/agontier/z3/build/z3 /Users/agontier/Desktop/crypto/trivium.cnf`, stdout = "/Users/agontier/Desktop/crypto/res.out"))
end
function printv(v)
        for b in v
                print(if b "1" else "."end)
        end
        println()
end
function interpret()
        open(string(ch,"res.out"),"r") do s
                s = readlines(s)
                if s[1]=="sat"
                        ss=[parse(Int,i)>0 for i in split(s[2][1:end-1]," ")]
                        print("\nK :")
                        printv(ss[1:80])
                        print("\nIV:")
                        printv(ss[94:173])
                        println("\nThere is at least one trail for K at z",Nb," but SAT needs to count now")
                else
                        println("UNSAT there must be an error")
                end
        end
end

function main()
        println("Writing trivium.cnf DIMACS monomial propag model")
        generate();println("\n    Solving with z3\n")
        solve()
        z=interpret()
end

main()

#=
/Applications/Julia-1.5.app/Contents/Resources/julia/bin/julia /Users/agontier/Desktop/crypto/triviumSATbrut.jl
/Users/agontier/z3/build/z3 /Users/agontier/Desktop/crypto/trivium.cnf > /Users/agontier/Desktop/crypto/res.out
=#

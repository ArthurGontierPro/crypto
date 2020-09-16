ch="/Users/agontier/Desktop/crypto/"
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
function shift(f,s)
        for x in s
                write(f,string(x," -",x-290," 0\n"))
                write(f,string(x-290," -",x," 0\n"))
        end
end
function ft(f,a,b,c,d,e,t)
        write(f,string("-",t," -",e," -",d," -",b," -",c," ",a," 0\n"))
        write(f,string("-",t," -",e," -",d," ",b," -",a," 0\n"))
        write(f,string("-",t," -",e," -",d," ",c," -",a," 0\n"))
        write(f,string("-",t," -",e," ",d," -",b," -",c," -",a," 0\n"))
        write(f,string("-",t," -",e," ",d," ",b," ",a," 0\n"))
        write(f,string("-",t," -",e," ",d," ",c," ",a," 0\n"))
        write(f,string("-",t," ",e," -",d," -",b," -",c," -",a," 0\n"))
        write(f,string("-",t," ",e," -",d," ",b," ",a," 0\n"))
        write(f,string("-",t," ",e," -",d," ",c," ",a," 0\n"))
        write(f,string("-",t," ",e," ",d," -",b," -",c," ",a," 0\n"))
        write(f,string("-",t," ",e," ",d," ",b," -",a," 0\n"))
        write(f,string("-",t," ",e," ",d," ",c," -",a," 0\n"))
        write(f,string(t," -",e," -",d," -",b," -",c," -",a," 0\n"))
        write(f,string(t," -",e," -",d," ",b," ",a," 0\n"))
        write(f,string(t," -",e," -",d," ",c," ",a," 0\n"))
        write(f,string(t," -",e," ",d," -",b," -",c," ",a," 0\n"))
        write(f,string(t," -",e," ",d," ",b," -",a," 0\n"))
        write(f,string(t," -",e," ",d," ",c," -",a," 0\n"))
        write(f,string(t," ",e," -",d," -",b," -",c," ",a," 0\n"))
        write(f,string(t," ",e," -",d," ",b," -",a," 0\n"))
        write(f,string(t," ",e," -",d," ",c," -",a," 0\n"))
        write(f,string(t," ",e," ",d," -",b," -",c," -",a," 0\n"))
        write(f,string(t," ",e," ",d," ",b," ",a," 0\n"))
        write(f,string(t," ",e," ",d," ",c," ",a," 0\n"))
end
function fz(f,a)
        open(string(ch,"fz.cnf"),"r") do d
	        s = readlines(d)
	        for c in s
                        cc=split(c," ")
                        s=""
                        for i in cc
                                ii=parse(Int,i)
                                if ii==0
                                        s=string(s," 0\n")
                                elseif ii<0
                                        s=string(s," -",a[ii*(-1)])
                                else
                                        s=string(s," ",a[ii])
                                end
	                end
                        write(f,s)
                end
        end
end
function f(f,r)
        rn=r*289
        ro=(r-1)*289
        ft(f,243+ro,286+ro,287+ro,288+ro,69+ro,1+rn)
        shift(f,[i for i in 2+rn:93+rn])
        ft(f,66+ro,91+ro,92+ro,93+ro,171+ro,94+rn)
        shift(f,[i for i in 95+rn:177+rn])
        ft(f,162+ro,175+ro,176+ro,177+ro,264+ro,178+rn)
        shift(f,[i for i in 179+rn:288+rn])
        if r>4*288
                print(r,' ')
                fz(f,[162+ro,175+ro,176+ro,177+ro,264+ro,66+ro,91+ro,92+ro,93+ro,171+ro,243+ro,286+ro,287+ro,288+ro,69+ro,289+rn])
        end
end

function generate()
        open(string(ch,"trivium.cnf"),"w") do d
                fixx(d,[i for i in 1:80],true)#K
                fixx(d,[i for i in 81:93],false)
                fixx(d,[i for i in 94:173],true)#IV
                fixx(d,[i for i in 174:285],false)
                fixx(d,[i for i in 286:288],true)
                for r in 1:(4*288+288)
                        f(d,r)
                end
        end
end

function printv(v)
        for b in v
                print(if b "1" else "."end)
        end
        println()
end
function printz(i,ss)
        println("z",i,"=",ss[(4*288+i)*289])
end
function prints(r,ss)
        println("r=",r)
        print("s1:93   =")
        printv(ss[r*289+1:r*289+93])
        print("s94:176 =")
        printv(ss[r*289+94:r*289+176])
        print("s177:288=")
        printv(ss[r*289+177:r*289+288])
        if r>4*288 println("z",r-4*288,"=",ss[r*289+289]) end
end
function interpret()
        open(string(ch,"res.out"),"r") do s
                s = readlines(s)
                ss=[parse(Int,i)>0 for i in split(s[2]," ")[1:end-1]]

                for r in 288*4:288*4+288
                        prints(r,ss)
                end
                for r in 0:5
                        prints(r,ss)
                end
        end
end

println("Writing trivium.cnf DIMACS model")


#generate()


println("\n Solving with z3")


#run(pipeline(`/Users/agontier/z3/build/z3 /Users/agontier/Desktop/crypto/trivium.cnf`, stdout = "/Users/agontier/Desktop/crypto/res.out"))


interpret()

#=
/Applications/Julia-1.5.app/Contents/Resources/julia/bin/julia /Users/agontier/Desktop/crypto/triviumSATbrut.jl
/Users/agontier/z3/build/z3 /Users/agontier/Desktop/crypto/trivium.cnf > /Users/agontier/Desktop/crypto/res.out
=#


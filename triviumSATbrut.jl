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
function fixz(f,z)
        i=4*288+1
        for b in z
                fixx(f,i*289,b==1)
                i=i+1
        end
end
function shift(f,s)
        for x in s
                write(f,string(x," -",x-290," 0\n"))
                write(f,string(x-290," -",x," 0\n"))
        end
end
function fcnf(f,fc,a)
        open(string(ch,fc,".cnf"),"r") do d
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
        fcnf(f,"ft",[243+ro,286+ro,287+ro,288+ro,69+ro,1+rn])
        shift(f,[i for i in 2+rn:93+rn])
        fcnf(f,"ft",[66+ro,91+ro,92+ro,93+ro,171+ro,94+rn])
        shift(f,[i for i in 95+rn:177+rn])
        fcnf(f,"ft",[162+ro,175+ro,176+ro,177+ro,264+ro,178+rn])
        shift(f,[i for i in 179+rn:288+rn])
        if r>4*288
                #print(r,' ')
                fcnf(f,"fz",[1+rn,94+rn,178+rn,289+rn])
        else
                fixx(f,289+ro,false)
        end
end

function generate()
        z=[0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0]#[1,1,1,1,1,0,1,1,1,1,1,0,0,0,0,0,1,0,1,1,1,1,1,1,0,0,1,0,0,1,1,0,0,1,0,1,1,0,0,0,0,1,0,1,1,0,0,1,0,0,0,0,0,1,0,1,0,0,0,1,1,0,1,1,0,1,0,1,0,0,0,1,0,1,1,1,1,0,1,0,0,0,1,0,1,1,1,0,0,1,0,0,1,1,1,0,0,0,1,0,0,0,1,1,1,0,0,1,1,1,1,1,1,1,0,0,1,0,0,1,0,1,1,1,1,1,1,1]# generated with K = 0 & IV = 0
        open(string(ch,"trivium.cnf"),"w") do d
                #fixx(d,[i for i in 1:80],false)#K
                fixx(d,[i for i in 81:93],false)
                fixx(d,[i for i in 94:173],false)#IV
                fixx(d,[i for i in 174:285],false)
                fixx(d,[i for i in 286:288],true)
                fixz(d,z)
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
function z(i) return (4*288+i)*289 end 
function printz(i,ss)
        println("z",i,"=",ss[z(i)])
end
function getzs(s) return [i for i in s[(4*288+1)*289:289:5*288*289]] end
function prints(r,ss)
        println("r=",r)
        print("s1:93   =")
        printv(ss[r*289+1:r*289+93])
        print("s94:177 =")
        printv(ss[r*289+94:r*289+177])
        print("s178:288=")
        printv(ss[r*289+178:r*289+288])
        if r>4*288 println("z",r-4*288,"=",ss[r*289+289]) end
end
function interpret()
        open(string(ch,"res.out"),"r") do s
                s = readlines(s)
                if s[1]=="sat"
                        println(length(s[2])-length(s[2][1:end-1]))
                        ss=[parse(Int,i)>0 for i in split(s[2][1:end-1]," ")]
                        for r in 1:288*4+288
                                #prints(r,ss)
                        end
                        print("\nK       =")
                        println(ss[1:80])
                        print("\nIV       =")
                        println(ss[94:173])
                        print("\nZ1:288  =")
                        println(getzs(ss))
                else
                        println("UNSAT there must be an error")
                end
        end
end

println("Writing trivium.cnf DIMACS model")


generate()


println("\n    Solving with z3\n")


run(pipeline(`/Users/agontier/z3/build/z3 /Users/agontier/Desktop/crypto/trivium.cnf`, stdout = "/Users/agontier/Desktop/crypto/res.out"))


interpret()

#=
/Applications/Julia-1.5.app/Contents/Resources/julia/bin/julia /Users/agontier/Desktop/crypto/triviumSATbrut.jl
/Users/agontier/z3/build/z3 /Users/agontier/Desktop/crypto/trivium.cnf > /Users/agontier/Desktop/crypto/res.out
=#

function verif1(a,b,c,d,e,t) return (a ⊻ (b & c) ⊻ d ⊻ e) == t end
function verif2(a,b,c,d,e,t) return (a ⊻ (b & c) ⊻ d ⊻ e) ⊻ t ⊻ 1 end
function verif3(a,b,c,d,e,t) return (~t|~e|~d|~b|~c|a)&(~t|~e|~d|b|~a)&(~t|~e|~d|c|~a)&(~t|~e|d|~b|~c|~a)&(~t|~e|d|b|a)&(~t|~e|d|c|a)&(~t|e|~d|~b|~c|~a)&(~t|e|~d|b|a)&(~t|e|~d|c|a)&(~t|e|d|~b|~c|a)&(~t|e|d|b|~a)&(~t|e|d|c|~a)&(t|~e|~d|~b|~c|~a)&(t|~e|~d|b|a)&(t|~e|~d|c|a)&(t|~e|d|~b|~c|a)&(t|~e|d|b|~a)&(t|~e|d|c|~a)&(t|e|~d|~b|~c|a)&(t|e|~d|b|~a)&(t|e|~d|c|~a)&(t|e|d|~b|~c|~a)&(t|e|d|b|a)&(t|e|d|c|a) end#+1
function verif4(a,b,c,d,e,t) return (-t|-e|-d|-b|-c|-a)&(-t|-e|-d|b|a)&(-t|-e|-d|c|a)&(-t|-e|d|-b|-c|a)&(-t|-e|d|b|-a)&(-t|-e|d|c|-a)&(-t|e|-d|-b|-c|a)&(-t|e|-d|b|-a)&(-t|e|-d|c|-a)&(-t|e|d|-b|-c|-a)&(-t|e|d|b|a)&(-t|e|d|c|a)&(t|-e|-d|-b|-c|a)&(t|-e|-d|b|-a)&(t|-e|-d|c|-a)&(t|-e|d|-b|-c|-a)&(t|-e|d|b|a)&(t|-e|d|c|a)&(t|e|-d|-b|-c|-a)&(t|e|-d|b|a)&(t|e|-d|c|a)&(t|e|d|-b|-c|a)&(t|e|d|b|-a)&(t|e|d|c|-a) end
function verif5(a,b,c,d,e,t) return ~((~t|~e|~d|~b|~c|a)&(~t|~e|~d|b|~a)&(~t|~e|~d|c|~a)&(~t|~e|d|~b|~c|~a)&(~t|~e|d|b|a)&(~t|~e|d|c|a)&(~t|e|~d|~b|~c|~a)&(~t|e|~d|b|a)&(~t|e|~d|c|a)&(~t|e|d|~b|~c|a)&(~t|e|d|b|~a)&(~t|e|d|c|~a)&(t|~e|~d|~b|~c|~a)&(t|~e|~d|b|a)&(t|~e|~d|c|a)&(t|~e|d|~b|~c|a)&(t|~e|d|b|~a)&(t|~e|d|c|~a)&(t|e|~d|~b|~c|a)&(t|e|~d|b|~a)&(t|e|~d|c|~a)&(t|e|d|~b|~c|~a)&(t|e|d|b|a)&(t|e|d|c|a)) end#neg (...+1)
function verif6(a,b,c,d,e,t) return (t|~e|~d|~b|~c|a)&(t|~e|~d|b|~a)&(t|~e|~d|c|~a)&(t|~e|d|~b|~c|~a)&(t|~e|d|b|a)&(t|~e|d|c|a)&(t|e|~d|~b|~c|~a)&(t|e|~d|b|a)&(t|e|~d|c|a)&(t|e|d|~b|~c|a)&(t|e|d|b|~a)&(t|e|d|c|~a)&(~t|~e|~d|~b|~c|~a)&(~t|~e|~d|b|a)&(~t|~e|~d|c|a)&(~t|~e|d|~b|~c|a)&(~t|~e|d|b|~a)&(~t|~e|d|c|~a)&(~t|e|~d|~b|~c|a)&(~t|e|~d|b|~a)&(~t|e|~d|c|~a)&(~t|e|d|~b|~c|~a)&(~t|e|d|b|a)&(~t|e|d|c|a) end# neg t ... +1
F2=[false,true]
sum([verif1(a,b,c,d,e,t)==verif2(a,b,c,d,e,t) for t in F2, e in F2, d in F2, c in F2, b in F2, a in F2])==2^6
sum([verif1(a,b,c,d,e,t)==verif3(a,b,c,d,e,t) for t in F2, e in F2, d in F2, c in F2, b in F2, a in F2])==2^6
sum([verif1(a,b,c,d,e,t)==verif4(a,b,c,d,e,t) for t in F2, e in F2, d in F2, c in F2, b in F2, a in F2])==2^6
sum([verif1(a,b,c,d,e,t)==verif5(a,b,c,d,e,t) for t in F2, e in F2, d in F2, c in F2, b in F2, a in F2])==2^6
sum([verif1(a,b,c,d,e,t)==verif6(a,b,c,d,e,t) for t in F2, e in F2, d in F2, c in F2, b in F2, a in F2])==2^6

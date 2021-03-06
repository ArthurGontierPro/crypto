ch="/Users/agontier/Desktop/crypto/"
global rinit = 1152
global rz = 288
function isat(r,i)
        if r==0
                return i
        elseif i==1
                return 289+(r-1)*4+1
        elseif i==94
                return 289+(r-1)*4+2
        elseif i==178
                return 289+(r-1)*4+3
        elseif i==289
                return 289+(r-1)*4+4
        else
                return isat(r-1,i-1)
        end
end
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
        r=rinit+1
        for b in z
                fixx(f,isat(r,289),b==1)
                r=r+1
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
        fcnf(f,"ft",[isat(r-1,243),isat(r-1,286),isat(r-1,287),isat(r-1,288),isat(r-1,69),isat(r,1)])
        fcnf(f,"ft",[isat(r-1,66),isat(r-1,91),isat(r-1,92),isat(r-1,93),isat(r-1,171),isat(r,94)])
        fcnf(f,"ft",[isat(r-1,162),isat(r-1,175),isat(r-1,176),isat(r-1,177),isat(r-1,264),isat(r,178)])
        if r>rinit
                #print(r,' ')
                fcnf(f,"fz",[isat(r-1,66),isat(r-1,93),isat(r-1,162),isat(r-1,177),isat(r-1,243),isat(r-1,288),isat(r,289)])
        else
                fixx(f,isat(r,289),false)
        end
end

function generate(K=true,IV=true,z=[])
        open(string(ch,"trivium.cnf"),"w") do d
                if K fixx(d,[i for i in 1:80],false) end
                fixx(d,[i for i in 81:93],false)
                if IV fixx(d,[i for i in 94:173],false) end
                fixx(d,[i for i in 174:285],false)
                fixx(d,[i for i in 286:288],true)
                fixx(d,289,false)
                if length(z)>0 fixz(d,z) end
                for r in 1:(rinit+rz)
                        f(d,r)
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
function z(i) return (rinit+i)*289 end 
function printz(i,ss)
        println("z",i,"=",ss[z(i)])
end
function getzs(s) return [s[isat(r,289)]==1 for r in rinit+1:rinit+rz] end
function prints(r,ss)
        println("r=",r)
        print("s1:93   =")
        printv([ss[isat(r,i)] for i in 1:93])
        print("s94:177 =")
        printv([ss[isat(r,i)] for i in 94:177])
        print("s178:288=")
        printv([ss[isat(r,i)] for i in 178:288])
        if r>rinit println("z",r-rinit,"=",ss[isat(r,289)]) end
end
function interpret()
        open(string(ch,"res.out"),"r") do s
                s = readlines(s)
                if s[1]=="sat"
                        ss=[parse(Int,i)>0 for i in split(s[2][1:end-1]," ")]
                        for r in 1:rinit+rz
                                #prints(r,ss)
                        end
                        print("\nK       =")
                        printv(ss[1:80])
                        print("\nIV      =")
                        printv(ss[94:173])
                        print("\nZ1:",rz,"  =")
                        z=getzs(ss)
                        printv(z)
                        return z
                else
                        println("UNSAT there must be an error")
                end
        end
end

function main()
        println("Init rounds: ",rinit,", z rounds: ",rz)
        println("Writing trivium.cnf DIMACS model")
        generate();println("\n    Solving with z3\n")
        solve()
        z=interpret()

        println("Writing trivium.cnf without key")
        generate(false,true,z);println("\n    Solving with z3\n")
        t=time()
        solve()
        println("Key found in ",time()-t," seconds")
        zz=interpret()
end

main()

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
verif=false
if verif
        sum([verif1(a,b,c,d,e,t)==verif2(a,b,c,d,e,t) for t in F2, e in F2, d in F2, c in F2, b in F2, a in F2])==2^6
        sum([verif1(a,b,c,d,e,t)==verif3(a,b,c,d,e,t) for t in F2, e in F2, d in F2, c in F2, b in F2, a in F2])==2^6
        sum([verif1(a,b,c,d,e,t)==verif4(a,b,c,d,e,t) for t in F2, e in F2, d in F2, c in F2, b in F2, a in F2])==2^6
        sum([verif1(a,b,c,d,e,t)==verif5(a,b,c,d,e,t) for t in F2, e in F2, d in F2, c in F2, b in F2, a in F2])==2^6
        sum([verif1(a,b,c,d,e,t)==verif6(a,b,c,d,e,t) for t in F2, e in F2, d in F2, c in F2, b in F2, a in F2])==2^6
end

#[println(r,' ',i,' ',ii(r,i)) for i in 1:288, r in 1:288:288*5+1]
        #[1,1,1,1,1,0,1,1,1,1,1,0,0,0,0,0,1,0,1,1,1,1,1,1,0,0,1,0,0,1,1,0,0,1,0,1,1,0,0,0,0,1,0,1,1,0,0,1,0,0,0,0,0,1,0,1,0,0,0,1,1,0,1,1,0,1,0,1,0,0,0,1,0,1,1,1,1,0,1,0,0,0,1,0,1,1,1,0,0,1,0,0,1,1,1,0,0,0,1,0,0,0,1,1,1,0,0,1,1,1,1,1,1,1,0,0,1,0,0,1,0,1,1,1,1,1,1,1]# generated with K = 0 & IV = 0

function fz(f,a)
        open("fz.cnf") do d
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

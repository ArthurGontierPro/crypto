open("fz.cnf") do d
        open("fzold.jl","w") do f
                write(f,String("function fz(fic,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,z)\n"))
                v=['a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','z']
	        s = readlines(d)
	        for c in s
                        cc=split(c," ")
                        s="write(fic,string("
                        for i in cc
                                ii=parse(Int,i)
                                if ii==0
                                        s=string(s,'"',' ','0','/','n','"',')',')')
                                elseif ii<0
                                        s=string(s,'"',' ','-','"',',',v[ii*(-1)],',')
                                else
                                        s=string(s,'"',' ','"',',',v[ii],',')
                                end
	                end
                        write(f,string(s,"\n"))
                end
                write(f,String("end"))
        end
end

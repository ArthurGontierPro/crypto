Sbox = [    
0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76,
0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0,
0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15,
0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75,
0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84,
0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf,
0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8,
0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2,
0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73,
0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb,
0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79,
0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08,
0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a,
0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e,
0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf,
0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16]
rcon = [
0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a,
0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39,
0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a,
0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8,
0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef,
0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc,
0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b,
0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3,
0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94,
0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20,
0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35,
0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f,
0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04,
0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63,
0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd,
0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d]
m2=[
0x00,0x02,0x04,0x06,0x08,0x0a,0x0c,0x0e,0x10,0x12,0x14,0x16,0x18,0x1a,0x1c,0x1e,
0x20,0x22,0x24,0x26,0x28,0x2a,0x2c,0x2e,0x30,0x32,0x34,0x36,0x38,0x3a,0x3c,0x3e,
0x40,0x42,0x44,0x46,0x48,0x4a,0x4c,0x4e,0x50,0x52,0x54,0x56,0x58,0x5a,0x5c,0x5e,
0x60,0x62,0x64,0x66,0x68,0x6a,0x6c,0x6e,0x70,0x72,0x74,0x76,0x78,0x7a,0x7c,0x7e,
0x80,0x82,0x84,0x86,0x88,0x8a,0x8c,0x8e,0x90,0x92,0x94,0x96,0x98,0x9a,0x9c,0x9e,
0xa0,0xa2,0xa4,0xa6,0xa8,0xaa,0xac,0xae,0xb0,0xb2,0xb4,0xb6,0xb8,0xba,0xbc,0xbe,
0xc0,0xc2,0xc4,0xc6,0xc8,0xca,0xcc,0xce,0xd0,0xd2,0xd4,0xd6,0xd8,0xda,0xdc,0xde,
0xe0,0xe2,0xe4,0xe6,0xe8,0xea,0xec,0xee,0xf0,0xf2,0xf4,0xf6,0xf8,0xfa,0xfc,0xfe,
0x1b,0x19,0x1f,0x1d,0x13,0x11,0x17,0x15,0x0b,0x09,0x0f,0x0d,0x03,0x01,0x07,0x05,
0x3b,0x39,0x3f,0x3d,0x33,0x31,0x37,0x35,0x2b,0x29,0x2f,0x2d,0x23,0x21,0x27,0x25,
0x5b,0x59,0x5f,0x5d,0x53,0x51,0x57,0x55,0x4b,0x49,0x4f,0x4d,0x43,0x41,0x47,0x45,
0x7b,0x79,0x7f,0x7d,0x73,0x71,0x77,0x75,0x6b,0x69,0x6f,0x6d,0x63,0x61,0x67,0x65,
0x9b,0x99,0x9f,0x9d,0x93,0x91,0x97,0x95,0x8b,0x89,0x8f,0x8d,0x83,0x81,0x87,0x85,
0xbb,0xb9,0xbf,0xbd,0xb3,0xb1,0xb7,0xb5,0xab,0xa9,0xaf,0xad,0xa3,0xa1,0xa7,0xa5,
0xdb,0xd9,0xdf,0xdd,0xd3,0xd1,0xd7,0xd5,0xcb,0xc9,0xcf,0xcd,0xc3,0xc1,0xc7,0xc5,
0xfb,0xf9,0xff,0xfd,0xf3,0xf1,0xf7,0xf5,0xeb,0xe9,0xef,0xed,0xe3,0xe1,0xe7,0xe5]
m3=[
0x00,0x03,0x06,0x05,0x0c,0x0f,0x0a,0x09,0x18,0x1b,0x1e,0x1d,0x14,0x17,0x12,0x11,
0x30,0x33,0x36,0x35,0x3c,0x3f,0x3a,0x39,0x28,0x2b,0x2e,0x2d,0x24,0x27,0x22,0x21,
0x60,0x63,0x66,0x65,0x6c,0x6f,0x6a,0x69,0x78,0x7b,0x7e,0x7d,0x74,0x77,0x72,0x71,
0x50,0x53,0x56,0x55,0x5c,0x5f,0x5a,0x59,0x48,0x4b,0x4e,0x4d,0x44,0x47,0x42,0x41,
0xc0,0xc3,0xc6,0xc5,0xcc,0xcf,0xca,0xc9,0xd8,0xdb,0xde,0xdd,0xd4,0xd7,0xd2,0xd1,
0xf0,0xf3,0xf6,0xf5,0xfc,0xff,0xfa,0xf9,0xe8,0xeb,0xee,0xed,0xe4,0xe7,0xe2,0xe1,
0xa0,0xa3,0xa6,0xa5,0xac,0xaf,0xaa,0xa9,0xb8,0xbb,0xbe,0xbd,0xb4,0xb7,0xb2,0xb1,
0x90,0x93,0x96,0x95,0x9c,0x9f,0x9a,0x99,0x88,0x8b,0x8e,0x8d,0x84,0x87,0x82,0x81,
0x9b,0x98,0x9d,0x9e,0x97,0x94,0x91,0x92,0x83,0x80,0x85,0x86,0x8f,0x8c,0x89,0x8a,
0xab,0xa8,0xad,0xae,0xa7,0xa4,0xa1,0xa2,0xb3,0xb0,0xb5,0xb6,0xbf,0xbc,0xb9,0xba,
0xfb,0xf8,0xfd,0xfe,0xf7,0xf4,0xf1,0xf2,0xe3,0xe0,0xe5,0xe6,0xef,0xec,0xe9,0xea,
0xcb,0xc8,0xcd,0xce,0xc7,0xc4,0xc1,0xc2,0xd3,0xd0,0xd5,0xd6,0xdf,0xdc,0xd9,0xda,
0x5b,0x58,0x5d,0x5e,0x57,0x54,0x51,0x52,0x43,0x40,0x45,0x46,0x4f,0x4c,0x49,0x4a,
0x6b,0x68,0x6d,0x6e,0x67,0x64,0x61,0x62,0x73,0x70,0x75,0x76,0x7f,0x7c,0x79,0x7a,
0x3b,0x38,0x3d,0x3e,0x37,0x34,0x31,0x32,0x23,0x20,0x25,0x26,0x2f,0x2c,0x29,0x2a,
0x0b,0x08,0x0d,0x0e,0x07,0x04,0x01,0x02,0x13,0x10,0x15,0x16,0x1f,0x1c,0x19,0x1a]

function prints(k)
    for i in 1:4
        for j in 1:4
            print(' ',string(s[j][i],base=16))
        end
        println()
    end
    println()
end
function printek(ek)
    for i in 1:length(ek)
        prints(ek[i])
    end
end

function rw(x)
    return [x[2],x[3],x[4],x[1]]
end
function sw(x)
    return [Sbox[Int(x)+1] for x in x]
end
function rc(x)
    return [rcon[Int(x)+1],0x00,0x00,0x00]
end
function KeyExpantion(k)
    ek = [[k[1:4], k[5:8], k[9:12], k[13:16]] for i in 1:11]
    for r in 1:10
        k1 = sw(rw(ek[r][4]))
        k1 = k1 .⊻ ek[r][1]
        ek[r+1][1] = k1 .⊻ rc(r)
        ek[r+1][2] = ek[r][2] .⊻ ek[r+1][1]
        ek[r+1][3] = ek[r][3] .⊻ ek[r+1][2]
        ek[r+1][4] = ek[r][4] .⊻ ek[r+1][3]
    end
    return ek
end
function sb(s) 
    return [sw(x) for x in s] 
end
function sr(s)
    return [[s[1][1],s[2][2],s[3][3],s[4][4]],[s[2][1],s[3][2],s[4][3],s[1][4]],[s[3][1],s[4][2],s[1][3],s[2][4]],[s[4][1],s[1][2],s[2][3],s[3][4]]]
end
function mc(s)
    return [[m2[w[1]+1] .⊻ m3[w[2]+1] .⊻ w[3] .⊻ w[4],
             w[1] .⊻ m2[w[2]+1] .⊻ m3[w[3]+1] .⊻ w[4],
             w[1] .⊻ w[2] .⊻ m2[w[3]+1] .⊻ m3[w[4]+1],
             m3[w[1]+1] .⊻ w[2] .⊻ w[3] .⊻ m2[w[4]+1]] for w in s]
end
function ark(s,r)
    return [s[i] .⊻ r[i] for i in 1:4]
end
function encrypt(s,k)
    ek = KeyExpantion(k)
    for r in 1:11
        if r>1
            s = sb(s)
            s = sr(s)
            if r<11 
                s = mc(s) 
            end
        end
        s = ark(s,ek[r])
    end
    return s
end

x = Vector{UInt8}("theblockbreakers")
x = [x[1:4],x[5:8],x[9:12],x[13:16]]
k = hex2bytes(b"2b7e151628aed2a6abf7158809cf4f3c")

x11=encrypt(x,k)





n = 2
function mod1(a,b)
    return if a<b a elseif mod(a,b)==0 b else mod(a,b) end
end
function div1(a,b)
    return div(a+b-1,b)
end
function pla(r,i,j,k)#i word, j byte, k bit, r round
	return k+8(j-1)+4*8(i-1)+4*4*8(r-1)
end
function alp(x)
	return div1(x,4*4*8),div1(mod1(x,4*4*8),4*8),div1(mod1(mod1(x,4*4*8),4*8),8),mod1(mod1(mod1(x,4*4*8),4*8),8)
end

function satfixstate(f,s,r)
    for i in 1:4, j in 1:4
        byte = string(s[j][i],base=2)
        miss = 8-length(byte)
        for k in 1:miss
            write(f,string("-",pla(r,i,j,k)," 0\n"))
        end
        for k in 1+miss:8
            if byte[k-miss]=='1'
                write(f,string( "",pla(r,i,j,k)," 0\n"))
            else
                write(f,string("-",pla(r,i,j,k)," 0\n"))
            end
        end
    end
end
function satxor(f,a,b,c)
    write(f,string("-",a," -",b," -",c," 0\n"))
    write(f,string("-",a, " ",b, " ",c," 0\n"))
    write(f,string( "",a," -",b, " ",c," 0\n"))
    write(f,string( "",a, " ",b," -",c," 0\n"))
end
function sataes1(x,x11)
    open("sataes1.cnf","w") do f
        satfixstate(f,x,1)
        satfixstate(f,x11,3)
        for i in 0:3, j in 0:3, k in 0:7
            satxor(f,pla(1,i,j,k),pla(2,i,j,k),pla(3,i,j,k))
        end
    end
end
function interpret(s,r)
    x = [[[zeros(Bool,8) for j in 1:4] for i in 1:4] for r in 1:11]
    ss = Vector{Int}(undef,pla(11,4,4,8))
    open("res.out") do f
	s = readlines(f)
	println(s[1])
	if s[1] == "SAT" || s[1] == "sat"
	    s = split(s[2]," ")
	    for i in 1:size(s,1)
		ss[i]=parse(Int,s[i])
	    end
	    for l in ss
		if l>0
		    r,i,j,k = alp(l)
		    x[r][i][j][k]=1
		end
	    end
	end
    end
end

ss = [i for i in 1:4*4*8*11]

#=
TUTO : https://www.davidwong.fr/blockbreakers/square_2_attack4rounds.html
n = 10
w = n-1
g = div(n,2)
q = n

function pla(i,j,k)
	return i + w*(j-1) + w*g*(k-1)
end  
function zpla(i,j,k,kk)
	return pla(w,g,q) + i + w*(j-1) + w*g*(k-1) + w*g*w*g*(kk-1)
end 

function s1(f)# glouton pour fixer la première semaine
	k = 1
	for j in 1:div(n,2), kk in 1:2
		write(f,string(pla(1,j,k)," 0\n"))
		k+=1
	end
end

function sat(w,g,q)
	open("satsts.cnf","w") do f
	s1(f)
	# ctr 1
	for i in 1:w, j in 1:g, kk in 1:q
		s = ""
		for k in 1:q
			if k != kk
				s = s*string(pla(i,j,k)," ")
			end
		end
		write(f,s*"0\n")	
	end
	for i in 1:w, j in 1:g, k in 1:q-2, kk in k+1:q-1, kkk in kk+1:q
		write(f,string("-",pla(i,j,k)," -",pla(i,j,kk)," -",pla(i,j,kkk)," 0\n"))
	end

	# ctr 2
	for i in 1:w, k in 1:q
		s = ""
		for j in 1:g
			s = s*string(pla(i,j,k)," ")
		end
		write(f,s*"0\n")
	end
	for i in 1:w, k in 1:q, j in 1:g-1, jj in j+1:g
		write(f,string("-",pla(i,j,k)," -",pla(i,jj,k)," 0\n"))
	end

	# ctr 3
	for k in 1:q, j in 1:g, i in 1:w-2, ii in i+1:w-1, iii in ii+1:w
		write(f,string("-",pla(i,j,k)," -",pla(ii,j,k)," -",pla(iii,j,k)," 0\n"))
	end

	# ctr 4
	for i in 1:w, j in 1:g, k in 1:q-1, kk in k+1:q
		write(f,string(zpla(i,j,k,kk)," -",pla(i,j,k)," -",pla(i,j,kk)," 0\n"))
	end
	for i in 1:w, j in 1:g, k in 1:q-1, kk in k+1:q
		write(f,string("-",zpla(i,j,k,kk)," ",pla(i,j,k)," 0\n"))
	end
	for i in 1:w, j in 1:g, k in 1:q-1, kk in k+1:q
		write(f,string("-",zpla(i,j,k,kk)," ",pla(i,j,kk)," 0\n"))
	end
	for k in 1:q-1, kk in k+1:q
		s = ""
		for i in 1:w, j in 1:g
			s = s*string(zpla(i,j,k,kk)," ")
		end
		write(f,s*"0\n")
	end
	end
end

sat(w,g,q)


global n
global w
global g
global q

function alp(x)
	k = min(div(x,w*g)+1,q)
	xx = x - w*g*(k-1)
	if xx == 0
		k=k-1	
		xx = w*g
	end
	j = min(div(xx,w)+1,g)
	i = xx-w*(j-1)
	if i==0
		j=j-1
		i=w
	end
	return i,j,k
end

function interpret()
	x = zeros(Int,w,g,q)
	ss = Vector{Int}(undef,zpla(w,g,q-1,q)+1)
	open("res.out") do f
		s = readlines(f)
		println(s[1])
		if s[1] == "SAT" || s[1] == "sat"
			s = split(s[2]," ")
			for i in 1:size(s,1)
				ss[i]=parse(Int,s[i])
			end
		
			s = ss[1:pla(w,g,q)]
			;println(s)
			for l in 1:size(s,1)
				if s[l]>0
					i,j,k = alp(s[l])
					x[i,j,k]=1
				end
			end
			for i in 1:w
				println("Semaine ",i)
				for j in 1:g
					println("    terrain ",j," : ",findall(isodd,x[i,j,:]))
				end
			end
		end
	end
end


interpret()


=#

using JuMP, Gurobi#, CPLEX

function copy(a,b1,b2)
        @constraint(m,x[b1]+x[b2]>=x[a])
        @constraint(m,x[a]>=x[b1])
        @constraint(m,x[a]>=x[b2])
end
function and(a,b)
        for i in a
                @constraint(m,x[i]==x[b])
        end
end
function xor(a,b)
        @constraint(m,sum(x[i] for i in a)==x[b])
end




n=10
global m = Model(with_optimizer(Gurobi.Optimizer))
@variable(m,x[1:n], binary=true)

copy(1,2,3)
and([4,5],6)
xor([7,8],9)

for i in [1,4,5]
        fix(m[:x][i], 1)
end








@time optimize!(m)

x = value.(m[:x])
println()
for i in 1:n
        print("x",i,"=",round(Int,x[i]),", ")
end
println()




      
#	 fix(m[:x][1,j,k], 1)
#        @constraint(m,ctr1[i=1:w,k=1:q], sum(x[i,j,k] for j in 1:g) == 1)



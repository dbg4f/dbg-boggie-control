data=read("/home/dmitri/projects/boggie/math/motor.txt",-1,2);
time=(data(:,2)-data(1,2))/1000;
angle=data(:,1);
angle=angle';
time=time';
f=[time;angle];

function e=G(a, z),
    e = z(2) - a(1)*(z(1)-a(2)+a(2)*%e^(-z(1)/a(2))); 
endfunction 

a0=[1;4];
[aa,er]=datafit(G,f,a0); 
model=aa(1)*(time+aa(2)*(%e^(-time/aa(2))-1));
disp('Result:');
disp(aa(1));
disp(aa(2));
// 1.4253491 0.1145948  

xtitle('Зависимость угловой скорости от времени','time','$\dot\phi,[\frac{рад}{сек}]$');
plot2d(time,model,[5]);
a=gca();
a.children.children(1).thickness=2;
plot2d(time,angle,[2]);
a.children.children(1).thickness=2;
a.children.children(1).text=["$\dot\phi(t)$"];

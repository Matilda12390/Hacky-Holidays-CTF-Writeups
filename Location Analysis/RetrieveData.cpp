#include <iostream>
#include <fstream>
#include <string>
#include <cstdio>
#include <memory>
#include <stdexcept>
#include <array> //I don't remember why I included all this, I am pretty sure you don't need all of it haha
using namespace std;

int main(){
string port = "17006"; //change this to whatever port number you are assigned
system(("redis-cli -h 136.243.68.77 -p "+port+" FT.CREATE coordinates ON JSON SCHEMA $.longitude AS long NUMERIC SORTABLE $.latitude AS lat NUMERIC SORTABLE").c_str());
//comment out the above after the first manual run of the program to set the scene. If you leave this in you end up with an "already exists" message which messes up the csv file
system(("redis-cli -h 136.243.68.77 -p "+port+" FT.SEARCH coordinates \"@lat:[-9000 9000] @long:[-18000 18000]\" LIMIT 0 3000 > out.txt").c_str());
time_t current_time;
current_time = time(NULL);
fstream newfile;
newfile.open("out.txt",ios::in); //open a file to perform read operation using file object
if (newfile.is_open()){   //checking whether the file is open
	string str;
	while(getline(newfile, str)){ //read data from file object and put it into string.
		if (str.length() > 40){
		 size_t pos = str.find("longitude");
		 pos += 10;
		 size_t pos2 = str.find(",\"lat");
		 size_t pos3 = pos2 - pos;
		 string longitude = str.substr (pos+1, pos3);
		 
		 pos = str.find("plate");
		 pos += 7;
		 pos2 = str.find(",\"lon");
		 pos3 = pos2 - pos;
		 string plate = str.substr (pos+1, pos3-2);
		 
		 pos = str.find("latitude");
		 pos += 10;
		 pos2 = str.find("}");
		 pos3 = pos2 - pos;
		 string lat = str.substr (pos, pos3);
		 cout << current_time << "," << plate << "," << longitude << lat << endl;
		}
	}
newfile.close(); //close the file object.
}
}

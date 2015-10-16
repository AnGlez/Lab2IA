#include <iostream> 
#include <vector> using namespace std;

using mat_t = vector<vector<int>>;
int longestEndingAt(int i, int j, mat_t &longestEndingAtMatrix, const mat_t &M) {
	if(longestEndingAtMatrix[i][j] != 0) return longestEndingAtMatrix[i][j];
	int longest = 1;
	for(auto dx : {
		-1, 0, 1}
		) {
		if((i + dx < 0) || (i + dx >= M.size())) continue;
		for(auto dy : {
			-1, 0, 1}
			) {
			if((j + dy < 0 ) || (j + dy >= M[0].size())) continue;
			if(M[i + dx][j + dy] == M[i][j] - 1) {
				auto current = longestEndingAt(i + dx, j + dy, longestEndingAtMatrix, M) + 1;
				if(current > longest) longest = current;
			}
		}
	}
	longestEndingAtMatrix[i][j] = longest;
	return longest;
}
int longest(mat_t M) {
	mat_t longestEndingAtMatrix(M.size(),vector<int>(M[0].size(), 0));
	int result = 0;
	for(int i = 0;
		i < M.size();
		i++) {
		for(int j = 0;
			j < M[0].size();
			j++) {
			auto d = longestEndingAt(i, j, longestEndingAtMatrix, M);
		if(d > result) result = d;
	}
}
return result;
}
int main() {
	mat_t A = {
		{
			2, 3, 4}
			,{
				8, 7, 7}
				,{
					5, 6, 5}
				}
				;
				cout << longest(A) << endl;
				return 0;
			}
			output: 4 complexity: O(n^2)
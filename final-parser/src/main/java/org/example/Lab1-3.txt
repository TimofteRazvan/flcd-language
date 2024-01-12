int n;
read n;
array arr [];
int sum;
int i;
sum = 0;
i = 0;
while (i < n) {
    int nr;
    read nr;
    arr[i] = nr;
    if (i % 2 == 0) {
        sum = sum + arr[i];
    }
}
write sum;
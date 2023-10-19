/*
Solution to sandwich numbers.
The algorithm tries all possible shared prefixes/suffixes and lengths of the number.
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

long long tenpows[13];

// length is the length of sandiwch number being considered.
// prefix is a number in the range [1, 1000000] representing the shared prefix/suffix.
// max is the maximum sandwich number allowed (the input number).
// maxLength is the number of digits in max.
// prefLength is the number of digits in prefix.
long long count(int length, int prefix, long long max, int maxLength, int prefLength)
{
  if(2*prefLength > length) return 0; // The prefix is too long for the length being considered
  int middle = length - 2 * prefLength; // The number of digits between the prefix and suffix
  if(length < maxLength) return tenpows[middle];
  if(length > maxLength) return 0;
  // Calculate the number formed by placing all zeroes in the middle
  long long val = (long long) prefix * (1 + tenpows[prefLength + middle]);
  if(val > max) return 0;
  int i;
  long long res = 1;
  // Try increasing all digits in the middle, from most to least significant.
  // Only increase a digit if the resulting number is <= max.
  for(i = middle + prefLength - 1; i >= prefLength; i--)
  {
    int j;
    for(j = 0; j<9; j++)
    {
      if(val + tenpows[i] <= max)
      {
        val += tenpows[i];
        res += tenpows[i-prefLength];
      }
    }
  }
  return res;
}

int sandwich(int x)
{
  char str[8];
  sprintf(str, "%d", x);
  int len = strlen(str);
  int i, j;
  for(i = 1; i*2 <= len; i++)
  {
    int good = 1;
    for(j = 0; j<i; j++)
    {
      good &= str[j] == str[len-i+j];
    }
    if(good) return 1;
  }
  return 0;
}

int main()
{
  int i, j;
  tenpows[0] = 1;
  for(i = 1; i<=12; i++) tenpows[i] = tenpows[i-1] * 10;
  int T, t;
  scanf("%d", &T);
  for(t = 0; t<T; t++)
  {
    long long n;
    scanf("%lld", &n);
    char maxstr[15];
    sprintf(maxstr, "%lld", n);
    int maxLength = strlen(maxstr);
    long long res = 0;
    int prefLength = 0;
    for(i = 1; i<=1000000; i++)
    {
      if(i == tenpows[prefLength]) prefLength++;
      if(sandwich(i) == 1) continue;
      for(j = 1; j<=13; j++)
        res += count(j, i, n, maxLength, prefLength);
    }
    printf("Number #%d: There are %lld sandwich numbers that meet our criteria.\n", t+1, res);
  }
  return 0;
}


# Solution to Sandwich Numbers from 2016-2017 UCF HSPT: Online Edition

import math

#Returns true if a number is sandwich number and false otherwise
def isSandwich(s):
    L = len(s)
    for i in range(1, int(L/2) + 1):
        if(s[0:i] == s[L-i:L]):
            return True
    return False

# Precompute for all numbers up to a million whether or no they are sandwich numbers
isSand = []
for i in range(0, 10):
    isSand.append(False)
for i in range(10, 1000001):
    isSand.append(isSandwich(str(i)))

# Read in number of test cases
T = int(input())
for t in range(1, T+1):
    n = int(input())
    s = str(n)
    L = len(s)
    # Special case: no sandwich numbers less than 10
    if(n < 10):
        print("Number #"+str(t)+": There are 0 sandwich numbers that meet our criteria.");
        continue;
    # Precompute array of all powers of 10
    pow10 = []
    pow10.append(1)
    last = 1
    for i in range(1, 18):
        last *= 10
        pow10.append(last)
    # Make arrays of prefixes and suffixes of the number
    # e.g., non-empty prefixes of "1243" are "1",  "12", "124", "1243"
    prefix = []
    suffix = []
    prefix.append(0)
    suffix.append(0)
    for i in range(1, L):
        prefix.append(int(s[0:i]));
    for i in range(1, L):
        suffix.append(int(s[L-i:L]));
    total = 0
    pow = 1
    for i in range(0, int(L/2)):
        pow *= 10
    # Try all possible first repeats of the outside of the sandwich
    for i in range(1, pow):
        # If the repeat is itself a sandwich number, we are double counting, so ignore them
        if isSand[i]:
            continue
        if i > prefix[int((L+1)/2)]:
            break
        l = int(math.log(float(i), 10) + 1e-9) + 1
        # Try all possible lengths for middle of the sandwich
        for j in range(0, L - 2*l + 1):
            if j == L - 2*l and i > prefix[l]:
                continue
            if j == L - 2 * l and i == prefix[l] and i > suffix[l]:
                continue
            if j != 0 and j == L - 2*l and i == prefix[l]:
                total += int(s[l:l+j]) + 1
                continue
            total += pow10[j]			
    print("Number #"+str(t)+": There are "+str(total)+" sandwich numbers that meet our criteria.");


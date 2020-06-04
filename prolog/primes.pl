%
% HW13 - HARD
%

init(LIMIT) :- mark_comp(2, 2, 4, LIMIT), sieve(3, LIMIT).

prime(N) :- N > 1, not(composite(N)).

sieve(CUR, LIMIT) :- SQUARE is CUR * CUR, sieve(CUR, SQUARE, LIMIT).
sieve(CUR, SQUARE, LIMIT) :- SQUARE > LIMIT, !.
sieve(CUR, SQUARE, LIMIT) :- mark_comp(CUR, SQUARE, LIMIT), !,
                       NEXT is CUR + 2, sieve(NEXT, LIMIT), !.

mark_comp(P, SQUARE, LIMIT) :- composite(P), !.
mark_comp(P, SQUARE, LIMIT) :- DIFF is P + P, mark_comp(P, DIFF, SQUARE, LIMIT).
mark_comp(P, DIFF, CUR, LIMIT) :- CUR > LIMIT, !.
mark_comp(P, DIFF, CUR, LIMIT) :- assert(composite(CUR)), assert(divisor(CUR, P)),
            NEXT is DIFF + CUR, mark_comp(P, DIFF, NEXT, LIMIT).

divisor(N, N) :- prime(N).
prime_divisors(1, []) :- !.
prime_divisors(N, [H | T]) :- number(N), divisor(N, H), A is div(N, H), prime_divisors(A, T), !.
prime_divisors(N, [H | T]) :- prime_divisors(N, [H | T], H), !.
prime_divisors(1, [], _) :- !.
prime_divisors(N, [H | T], M) :- H >= M, prime(H), prime_divisors(N1, T, H), N is N1 * H, !.

digit_list(0, K, []) :- !.
digit_list(N, K, [H | T]) :- H is mod(N, K), R is div(N, K), digit_list(R, K, T).
prime_palindrome(N, K) :- prime(N), digit_list(N, K, L), reverse(L, R), L == R.

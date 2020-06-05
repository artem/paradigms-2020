%
% HW13 - HARD
%

init(LIMIT) :- mark_comp(2, 2, 4, LIMIT), sieve(2, 3, LIMIT).

prime(N) :- N > 1, not(composite(N)).

table_nth(1, 2).
nth_prime(N, P) :- table_nth(N, P), !.
nth_prime(N, P) :- N1 is N - 1, nth_prime(N1, P1), NEXT is P1 + 2, next_prime(NEXT, P), assert(table_nth(N, P)), !.

next_prime(OLD, NEW) :- composite(OLD), NEXT is OLD + 2, next_prime(NEXT, NEW), !.
next_prime(OLD, NEW) :- OLD = NEW, !.

sieve(N, CUR, LIMIT) :- SQUARE is CUR * CUR, sieve(N, CUR, SQUARE, LIMIT).
sieve(N, CUR, SQUARE, LIMIT) :- SQUARE > LIMIT, !.
sieve(N, CUR, SQUARE, LIMIT) :- composite(CUR), !, NEXT is CUR + 2, sieve(N, NEXT, LIMIT).
sieve(N, CUR, SQUARE, LIMIT) :- assert(table_nth(N, CUR)), mark_comp(CUR, SQUARE, LIMIT), !,
                       NEXT is CUR + 2, N1 is N + 1, sieve(N1, NEXT, LIMIT).

divisor(N, N) :- prime(N), !.

mark_comp(P, SQUARE, LIMIT) :- composite(P), !.
mark_comp(P, SQUARE, LIMIT) :- DIFF is P + P, mark_comp(P, DIFF, SQUARE, LIMIT).
mark_comp(P, DIFF, CUR, LIMIT) :- CUR > LIMIT, !.
mark_comp(P, DIFF, CUR, LIMIT) :- assert(composite(CUR)), assert(divisor(CUR, P)),
            NEXT is DIFF + CUR, mark_comp(P, DIFF, NEXT, LIMIT).

prime_divisors(1, []) :- !.
prime_divisors(N, [H | T]) :- number(N), divisor(N, H), N1 is div(N, H), prime_divisors(N1, T), !.
prime_divisors(N, [H | T]) :- prime_divisors(N, [H | T], H), !.
prime_divisors(1, [], _) :- !.
prime_divisors(N, [H | T], MIN) :- H >= MIN, prime(H), prime_divisors(N1, T, H), N is N1 * H, !.

digit_list(0, K, []) :- !.
digit_list(N, K, [H | T]) :- H is mod(N, K), R is div(N, K), digit_list(R, K, T).
prime_palindrome(N, K) :- prime(N), digit_list(N, K, L), reverse(L, R), L == R.

map_split(empty, V, empty, empty) :- !.

map_split(node(K, V, P, L, R), X, node(K, V, P, L, R1), Res2) :-
	K < X, map_split(R, X, R1, Res2), !.

map_split(node(K, V, P, L, R), X, Res1, node(K, V, P, L1, R)) :-
	map_split(L, X, Res1, L1), !.

map_merge(F, empty, F) :- !.
map_merge(empty, S, S) :- !.

map_merge(node(K1, V1, P1, L1, R1), node(K2, V2, P2, L2, R2), node(K1, V1, P1, L1, Result)) :-
	P1 > P2, map_merge(R1, node(K2, V2, P2, L2, R2), Result), !.

map_merge(node(K1, V1, P1, L1, R1), node(K2, V2, P2, L2, R2), node(K2, V2, P2, Result, R2)) :-
	map_merge(node(K1, V1, P1, L1, R1), L2, Result), !.

map_get(node(K, V, _, _, _), K, V) :- !.
map_get(node(K, _, _, L, R), Key, V) :- Key < K, map_get(L, Key, V), !.
map_get(node(K, _, _, L, R), Key, V) :- map_get(R, Key, V), !.


map_put(Tree, K, V, Res) :-
	map_split(Tree, K, T1, T2), map_split(T2, K + 1, _, T3),
	rand_int(1000, RAMDOM),
	map_merge(T1, node(K, V, RAMDOM, empty, empty), T4),
	map_merge(T4, T3, Res), !.

map_remove(node(K, V, P, L, R), K, Res) :-
	map_split(Tree, K, T1, T2), map_split(T2, K + 1, _, T3),
	map_merge(T1, T3, Res).

map_build([], empty) :- !.
map_build([(K, V) | T], TreeMap) :- write((K, V)), nl, map_build(T, TreeMapS), map_put(TreeMapS, K, V, TreeMap).

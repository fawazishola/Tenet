# Tenet Language — 300 Deep Questions for Mastery

> **Purpose:** Adversarial, VC-grade questions that force you to defend every design choice. If you can answer these confidently, you built this language and you *know* it.

---

## Section 1: Philosophy & Vision (40 Questions)

### Core Identity

1. If someone says "Tenet is just Python with game theory functions," how do you prove them wrong in 30 seconds?
2. What specific failure mode in existing game theory tools (Gambit, Nashpy) made you decide Tenet needed to exist?
3. A skeptic asks: "Why build a new language instead of a better library for Python?" What's your answer?
4. What does Tenet do that literally no other tool in existence does? Be specific.
5. Explain the "notation gap" between game theory papers and code to a non-technical investor. Why does it matter?
6. Someone argues that the translation from payoff matrices to arrays is fine because "programmers learn it." Why is that insufficient?
7. You claim Tenet is "game-native." Give a concrete example where this prevents a real modeling error.
8. Why is "DSL for game theory" a better description than "game theory programming language"?
9. A Python developer says "games are just matrices." How do you show them they're wrong?
10. What would have to be true about strategic interaction modeling for Tenet to be unnecessary? Why isn't that true?

### Design Principles

11. Name Tenet's core design principles and explain why removing any one would break the language.
12. "Invalid games don't compile" sounds restrictive. Defend this to someone who thinks flexibility matters more.
13. What concrete decision did "games as first-class citizens" force you to make that you wouldn't have made otherwise?
14. A game theorist says "I just need to compute Nash equilibria fast." When is Tenet slower than raw NumPy, and why is that acceptable?
15. Give an example of a modeling error that Gambit calls "user error" that Tenet considers a syntax error.
16. Someone offers you a feature that would make Tenet 10x more popular but compromises game-theoretic correctness. What's an example and why do you refuse?
17. A junior researcher says "I don't care about formal definitions, I care about getting results." How do you respond without being condescending?
18. Why is being opinionated a feature? Hasn't Python succeeded by being unopinionated?
19. "Narrow by design" sounds like an excuse for missing features. Prove it's intentional strength.
20. Where did you compromise Tenet's purity for practicality? If never, why not?

### Vision & Goals

21. In 10 years, what does success look like for Tenet? Be specific about metrics (users, papers citing it, industries using it).
22. What would have to happen for you to consider Tenet a failure?
23. Why "canonical execution layer for game theory"? What's wrong with existing tools like Gambit?
24. "Game-theoretic operating system" sounds like marketing. What does it actually mean technically?
25. A VC asks: "You're not replacing Python — so what's the market?" How do you answer?
26. Describe your ideal Tenet user in detail. What problems do they have today?
27. What kind of person should actively avoid Tenet? Why is that okay?
28. You claim Tenet is suitable for mechanism design research. What specific properties make that true?
29. How does Tenet enable reproducibility that Python + Gambit can't achieve?
30. How is Tenet different from Gambit? Could the Gambit team just add your features?

### Philosophical Depth

31. What is Tenet's design manifesto? Explain what each principle means in practice.
32. "Game definitions are the interface" — why is this radical? What does it replace?
33. Most tools treat games as matrices. How does Tenet treat them differently, and why does that matter?
34. A pragmatist says "just make it work for everyone." Why is optimizing for everyone a mistake?
35. Give a concrete example of "correctness, clarity, and strategic integrity" in Tenet code.
36. You claim Tenet is closer to formal game theory than programming. What specific feature proves this?
37. What is the "cognitive tax" of using Nashpy? Quantify it if you can.
38. Why do economists avoid coding their models? How specifically does Tenet fix each reason?
39. Define "strategic drift" precisely. Give a before/after example in Python vs Tenet.
40. "Game-theoretic structure must be flattened into arrays" — flatten into what? Why is that bad?

---

## Section 2: Game Theory Foundations (40 Questions)

### Game Representation

41. What is a "game" in Tenet's type system? What components must it have?
42. Why distinguish between players, strategies, and payoffs at the language level?
43. A physicist models particle interactions as games. How would they represent continuous strategy spaces?
44. Give a specific bug that treating payoffs as raw numbers causes in Python that Tenet prevents.
45. When exactly is a game definition "complete"? What validation happens at compile time?
46. What error message should Tenet show for a payoff matrix missing a strategy combination? Design it.
47. How does Tenet represent mixed strategies differently from pure strategies? Why does it matter?
48. "A payoff is just a number" — explain to a CS student why this abstraction is wrong.
49. What does "strategy-aware typing" catch that unit tests wouldn't catch?
50. How does compile-time validation work when payoffs depend on runtime calculations?

### Nash Equilibrium

51. What is a Nash equilibrium? Explain it to someone who's never taken game theory.
52. Why must Nash equilibrium computation be a language primitive, not a library function?
53. How does Tenet distinguish pure-strategy Nash from mixed-strategy Nash?
54. What happens when a game has no pure-strategy Nash equilibrium? What does Tenet output?
55. A game can have multiple Nash equilibria. How does Tenet handle this? Does it return all of them?
56. What algorithm does Tenet use for Nash computation? Why that one over alternatives?
57. At what game size does Nash computation become intractable? How does Tenet warn users?
58. The Prisoner's Dilemma has a unique Nash equilibrium. Prove you understand why (Defect, Defect) is it.
59. What is a dominated strategy? Can Tenet detect and eliminate them automatically?
60. How does iterated elimination of dominated strategies (IEDS) relate to Nash equilibrium?

### Strategic Concepts

61. What is a best response? How does Tenet compute them?
62. Explain the difference between a zero-sum game and a non-zero-sum game. How does Tenet handle each?
63. What is Pareto efficiency? Can Tenet identify Pareto-optimal outcomes?
64. In the Battle of the Sexes, there are two pure Nash equilibria. Why might this cause coordination problems?
65. What is a mixed strategy? Why would a rational player ever randomize?
66. What is the minimax theorem? How does it relate to zero-sum games?
67. What is a correlated equilibrium? Is it stronger or weaker than Nash? Should Tenet support it?
68. What is a subgame perfect equilibrium? Does Tenet support extensive-form games?
69. A mechanism designer asks about incentive compatibility. What does that mean and how does Tenet help?
70. What is the revelation principle? Why might a DSL for game theory need to know about it?

### Game Classification

71. What's the difference between simultaneous and sequential games? Which does Tenet support?
72. Why is "normal form" vs "extensive form" an important distinction?
73. What is a symmetric game? Can Tenet detect symmetry automatically?
74. What is a repeated game? How does Tenet model the Prisoner's Dilemma played 100 times?
75. What are n-player games? How does computational complexity change with n?
76. What is incomplete information? How would Tenet represent a Bayesian game?
77. What is signaling in game theory? Give an example and explain how Tenet could model it.
78. What is cheap talk? How is it different from signaling?
79. What is mechanism design? Why is it "reverse game theory"?
80. What is auction theory? Could Tenet model a first-price sealed-bid auction?

---

## Section 3: Syntax & Game Definitions (35 Questions)

### Game Declaration Syntax

81. Walk through the syntax of `game PrisonersDilemma { ... }`. What does each token mean?
82. Why `players Alice, Bob` instead of `players = ["Alice", "Bob"]`? What's the semantic difference?
83. Why must strategies be explicitly declared? Couldn't Tenet infer them from payoffs?
84. What is the exact syntax for a payoff block? Parse `payoff Alice { (Cooperate, Cooperate): 3 }`.
85. Why use `(Strategy1, Strategy2): value` instead of a matrix literal? What's the advantage?
86. What AST nodes does the parser produce for a game declaration?
87. Show the difference between a 2-player and 3-player game syntax. What changes?
88. How do you handle asymmetric games where players have different strategy sets?
89. Can payoff values be expressions like `max_price - unit_cost`? How does that work?
90. What's the syntax for importing a game definition from another file?

### Solve Statement

91. What does `solve GameName;` actually do? Walk through the execution.
92. Why is `solve` a statement and not a function call like `solve(GameName)`?
93. What output does `solve` produce? Design the ideal output format.
94. Can you specify which type of equilibrium to solve for? What's the syntax?
95. What happens if you `solve` a game with no equilibrium?
96. Can you `solve` a game multiple times? Why would you want to?
97. How does `solve` interact with parameterized games (games with variables)?
98. What's the difference between `solve` and `analyze`? Should Tenet have both?
99. After solving, how do you access the equilibrium programmatically?
100. Can `solve` take options like `solve GameName with algorithm = "lemke-howson"`?

### Simulation Syntax

101. What does the `simulate` statement do? How is it different from `solve`?
102. Walk through `simulate GameName { Alice uses TitForTat; rounds 100; }`.
103. How do you define a custom strategy for simulation?
104. What is `history.last_opponent`? What other history accessors exist?
105. How does Tenet track state across rounds of a repeated game?
106. Can you simulate games with more than 2 players? What changes?
107. What output does simulation produce? Aggregate statistics? Per-round data?
108. Can you visualize simulation results? What tools does Tenet provide?
109. How do you compare two strategies against each other? Tournament syntax?
110. What happens if a strategy references undefined history (round 1)?

### Expressions & Variables

111. Is Tenet expression-oriented or statement-oriented? Why that choice?
112. What does `:=` mean in Tenet? How is it different from `=`?
113. Can you define variables inside a game block? What's the scoping rule?
114. How do you reference a player's strategy in an expression?
115. Can payoff expressions contain conditionals like `if condition then x else y`?

---

## Section 4: What Tenet Refuses (35 Questions)

### General-Purpose Programming

116. A VC asks "why limit your market by refusing general-purpose?" What's your answer?
117. List every use case Tenet explicitly refuses. Why each one?
118. Someone wants to build a web server in Tenet. What exactly do you tell them?
119. Why no file I/O? Isn't loading data from CSV a game theory workflow requirement?
120. Why no networking? Couldn't you simulate games over a network?
121. Someone writes a script mixing game definitions and database queries. What breaks? Why?
122. Prove that narrowness makes Tenet stronger, not weaker. Give a specific example.
123. What features did you NOT add because they'd compromise the core mission?
124. You meet a generalist programmer. What language should they use instead of Tenet?
125. "Tenet exists for game theory only" — how do you respond to "but my game needs external data"?

### Features Tenet Explicitly Rejects

126. Tenet has no graphics. A researcher asks "how do I visualize my game?" What's your answer?
127. Why no interoperability with R? Isn't R huge in economics?
128. "I want to call my Python model from Tenet." Why is this not a priority?
129. Tenet doesn't have a REPL debugger. Why not?
130. Why no support for stochastic games with continuous distributions?
131. Someone wants floating-point payoffs with 128-bit precision. Why don't you support that?
132. Why no import system for external game libraries?
133. Tenet doesn't have templates/generics. How do you write reusable game patterns?
134. Why no package manager? Isn't that table stakes for a modern language?
135. Is there ANY consumer feature you'd add if convinced? What would convince you?

### Dynamic Typing

136. A Python developer asks "why not let players just be strings?" Explain the danger.
137. What bug happens when strategies are untyped strings instead of declared identifiers?
138. What is "runtime ambiguity in game definitions"? Give a code example.
139. Why must payoff matrices be validated at compile time, not runtime?
140. Give 3 modeling bugs that static game typing prevents that tests wouldn't catch.
141. Compare Tenet's typing to Numpy's approach. What can Tenet prove that NumPy can't?
142. Can you write polymorphic games in Tenet? Show how type constraints work.
143. How do you handle generics if types are strict? What's the Tenet approach?
144. "A game is just a data structure" — why is this intuition wrong for a game theory language?
145. What's the cost of strict typing? When is that cost worth paying?

### Hidden Complexity

146. Give a Python example where computing Nash equilibria hides an exponential algorithm.
147. Why must expensive computation look expensive in game theory?
148. What's wrong with a function that looks simple but runs for hours?
149. How does Tenet make computational cost visible? Show an example.
150. Why is explicit game definition better than implicit game construction?

---

## Section 5: Solver Architecture (30 Questions)

### Nash Equilibrium Algorithms

151. Name at least 3 algorithms for computing Nash equilibria. What are the trade-offs?
152. What is the Lemke-Howson algorithm? Why is it good for 2-player games?
153. What is support enumeration? When is it faster or slower than Lemke-Howson?
154. How do you solve for Nash equilibria in n-player games? What algorithms exist?
155. What is the computational complexity of finding a Nash equilibrium? Is it NP-complete?
156. What is PPAD-completeness? Why does it matter for Nash computation?
157. Given a 2×2 game, can you compute the mixed Nash equilibrium by hand? Show the method.
158. What is the indifference principle? How does it relate to mixed equilibria?
159. When does a game have a unique Nash equilibrium? What properties guarantee uniqueness?
160. How do you verify that a strategy profile is a Nash equilibrium?

### Implementation Details

161. How is the payoff matrix represented internally? What data structure?
162. How do you handle floating-point precision when computing equilibria?
163. What tolerance should Tenet use for "close enough to equilibrium"?
164. How do you detect when an algorithm fails to converge?
165. What's the maximum game size Tenet can solve in reasonable time? What's "reasonable"?
166. How do you parallelize Nash computation for large games?
167. Should Tenet cache solved games? What are the trade-offs?
168. How would you implement incremental solving (game changes, update equilibrium)?
169. What numerical libraries does Tenet use internally? Why those?
170. How do you test that your Nash solver is correct? What test games verify correctness?

### Solver Output

171. After solving, what data does Tenet return? Design the output structure.
172. For mixed equilibria, how do you represent the probability distribution?
173. If there are multiple equilibria, do you return all of them or select one?
174. How do you communicate "no equilibrium found" vs "couldn't compute"?
175. Should Tenet show the solving steps? What debug output makes sense?
176. How do you visualize equilibria? Best response diagrams? Payoff matrices?
177. Can you export equilibria to other formats (JSON, CSV)?
178. What metadata should accompany an equilibrium (algorithm used, time, confidence)?
179. How do you compare equilibria? Notion of "better" equilibrium?
180. What about approximate equilibria? When is "close enough" acceptable?

---

## Section 6: Real Economic Models (40 Questions)

### Classic Games

181. Explain the Prisoner's Dilemma. Why is the Nash equilibrium suboptimal?
182. What is the Stag Hunt? How does it differ from the Prisoner's Dilemma?
183. What is the Chicken game (Hawk-Dove)? What real scenarios does it model?
184. What is Matching Pennies? Why is there no pure-strategy equilibrium?
185. What is the Battle of the Sexes? What coordination problem does it illustrate?
186. What is Rock-Paper-Scissors in game theory terms? What's the mixed equilibrium?
187. What game models arms races? What's the tragedy of that game?
188. What is the Centipede game? What does backward induction say about it?
189. What is the Ultimatum game? Why do humans reject "unfair" offers?
190. What is the Dictator game? What does it reveal about human preferences?

### Industrial Organization

191. What is Cournot competition? How do you model it in Tenet?
192. What is Bertrand competition? How does it differ from Cournot?
193. What is Stackelberg competition? How do you represent first-mover advantage?
194. What is a duopoly? How does the number of firms affect equilibrium?
195. What is price discrimination game-theoretically?
196. What is predatory pricing? Can you model it as a game?
197. What is entry deterrence? What strategies does the incumbent have?
198. What is a price war? Show the game that captures this dynamic.
199. What is tacit collusion? Why is it a repeated game problem?
200. What is the relationship between competition and welfare?

### Behavioral Economics

201. What is the Trust game? What does it reveal about human cooperation?
202. What is reciprocity? How do you model it as a modified payoff?
203. What is fairness in game theory? How do ultimatum rejections inform it?
204. What is a focal point (Schelling point)? How do players coordinate without communication?
205. What is bounded rationality? How might Tenet model less-than-perfect players?
206. What are social preferences? How do they modify the standard game model?
207. What is loss aversion? How does it change equilibrium predictions?
208. What is the endowment effect game-theoretically?
209. What is hyperbolic discounting? How does it affect repeated game strategies?
210. What is cheap talk? When is it credible?

### Political Economy

211. What is the Tragedy of the Commons? Model it as an n-player game.
212. What is the Public Goods game? Why is free-riding a problem?
213. What is voting as a game? What is the paradox of voting?
214. What is the median voter theorem? What game structure produces it?
215. What is lobbying game-theoretically? Who are the players?
216. What is the "race to the bottom" in regulatory competition?
217. What is international trade as a game? What's the prisoners' dilemma analogy?
218. What is climate cooperation game-theoretically? Why is it so hard?
219. What is arms control verification as a game?
220. What is collective action? What makes it succeed or fail?

---

## Section 7: Language Implementation (35 Questions)

### Lexer/Scanner

221. What tokens does Tenet have beyond a typical programming language?
222. How does the lexer handle `game`, `players`, `strategies`, `payoff`? Are they keywords or identifiers?
223. What is significant whitespace? Does Tenet use it? Why or why not?
224. How do you tokenize `(Cooperate, Cooperate): 3`? What tokens result?
225. How does the lexer handle comments?
226. What lexer errors are possible? Show the error messages you'd design.
227. Is the lexer hand-written or generated? Why that choice?
228. How do you handle Unicode player names? Is `players 玩家1, 玩家2` valid?
229. What's in the Token class? What fields does it have?
230. How does error recovery work in the lexer?

### Parser

231. What parsing technique does Tenet use? Recursive descent? Parser generator?
232. What is the grammar for a game declaration? Write it in EBNF.
233. How do you handle operator precedence in payoff expressions?
234. What is the AST for `game Test { players A, B; strategies X, Y; }`?
235. How does the parser report errors? What information does it provide?
236. What is the most complex production in Tenet's grammar?
237. How do you avoid ambiguity in the game syntax?
238. Can the parser recover from errors and continue parsing?
239. What's a tricky parsing case in Tenet? How do you resolve it?
240. How do you test the parser? What's your test suite strategy?

### Semantic Analysis

241. What semantic checks does Tenet perform after parsing?
242. How do you verify that every strategy pair has a payoff defined?
243. What scope rules does Tenet have? Can games reference outer variables?
244. How do you detect duplicate player names? Duplicate strategy names?
245. What is type checking in Tenet? What types exist?
246. How do you validate that payoff expressions evaluate to numbers?
247. What errors can only be caught in semantic analysis, not parsing?
248. Design the error message for a missing payoff: "Payoff for (X, Y) not defined for player A".
249. How do you implement the symbol table for games, players, strategies?
250. What optimization happens at the semantic analysis phase?

### Interpreter

251. Is Tenet interpreted or compiled? Why that choice?
252. What is tree-walking interpretation? How does it work for games?
253. How does the interpreter execute a `solve` statement?
254. How are game objects represented at runtime?
255. What is the environment? How does scoping work?

---

## Section 8: Comparison to Other Tools (35 Questions)

### vs Gambit

256. A Gambit power user asks "why use Tenet?" Give 3 compelling reasons they'd care about.
257. What can Tenet do that Gambit literally cannot do, even with enough code?
258. What can Gambit do that Tenet will never support? Why is that okay?
259. Gambit uses a C++ backend. What's Tenet's performance story?
260. Show a game definition in Gambit vs Tenet. What's more readable?
261. What's the learning curve for a Gambit user switching to Tenet?
262. Someone says "I'll stick with Gambit, it's proven." When are they right?
263. Gambit has a GUI. Does Tenet need one?
264. Gambit supports extensive-form games. Should Tenet?
265. Can Tenet call Gambit? Can Gambit call Tenet? What's the interop story?

### vs Nashpy

266. Explain Nashpy to someone who's never used it. What is it?
267. What does Tenet do better than Nashpy? Be specific.
268. What does Nashpy do better than Tenet? Be honest.
269. How does code readability compare? Show the same game in both.
270. Nashpy is a Python library. What's the advantage of a DSL instead?
271. Can a Nashpy user learn Tenet in a day? What's the migration path?
272. For what specific use case would a Nashpy expert prefer Tenet?
273. Nashpy uses NumPy. What does Tenet use internally?
274. How do the error messages compare? Show examples.
275. Could Nashpy just add Tenet's syntax? Why or why not?

### vs Building From Scratch

276. Why not just use NumPy directly? What's wrong with that approach?
277. Show the same game in raw Python vs Tenet. Count the lines.
278. What bugs are possible in hand-rolled game code that Tenet prevents?
279. How long does it take to learn game theory vs learn Tenet?
280. For a paper with 3 games, is Tenet overkill? Justify your answer.
281. An economist says "I'll just use Excel." What's wrong with that?
282. Someone writes game theory code in R. What problems do they face?
283. What if someone uses Mathematica for games? How does Tenet compare?
284. MATLAB has game theory toolboxes. How does Tenet differentiate?
285. Why would a professional choose Tenet over existing tools?

### vs Proof Assistants

286. Lean can formalize game theory. Tenet can compute game theory. What's the difference?
287. Could Tenet proofs be verified in Lean? What would that require?
288. Coq has game theory libraries. What do they offer that Tenet doesn't?
289. Is Tenet a theorem prover? Could it become one?
290. When would a Lean user switch to Tenet?

---

## Section 9: Axiom Stack Integration (25 Questions)

### Stack Position

291. Explain the Axiom Stack to someone with no context. What is it?
292. Draw the stack. Where is Tenet? What's above and below it?
293. How is Tenet different from Flux in purpose? Why are they separate?
294. Why not merge Tenet and Flux into one language?
295. What computation does Tenet delegate to Flux? Give an example.
296. Walk through a request that uses Tenet, Flux, and Axiom OS together.
297. What does Flux do that Tenet can't do? Why split them?
298. What does Axiom OS provide that standard Linux wouldn't?
299. What is the "reasoning-native operating system"? Define it precisely.
300. How does this stack differ from Python + Gambit + Nashpy on Linux?

---

## How to Use This Document

### Preparation Modes

1. **Random Fire:** Have someone pick 20 questions at random. Answer without notes. Record weak spots.

2. **Section Deep Dive:** Take one section. Answer every question out loud as if presenting.

3. **Adversarial Partner:** Have someone play skeptical VC. They ask, you defend.

4. **Gap Detection:** Any question you can't answer = either a knowledge gap or a design gap.

5. **Recording Practice:** Record yourself answering 10 questions. Listen back. Improve.

### Confidence Checklist

Before any interview or presentation, you should be able to:

- [ ] Explain Tenet in 30 seconds
- [ ] Name 3 bugs Tenet prevents that other tools allow
- [ ] Defend every "refusal" with a concrete example
- [ ] Compare Tenet to Gambit, Nashpy, NumPy without notes
- [ ] Explain Nash equilibrium to a non-expert
- [ ] Walk through a game definition parse → solve
- [ ] Describe the Axiom Stack architecture
- [ ] Explain why game theory needs its own language
- [ ] Give 5 real economic examples modeled in Tenet
- [ ] Name the algorithms behind `solve` and explain trade-offs

### Section Priorities by Audience

| Audience | Key Sections | Why |
|----------|--------------|-----|
| **VCs** | 1, 4, 9 | Vision, market, differentiation |
| **Economists** | 2, 6 | Game theory depth |
| **Engineers** | 3, 5, 7 | Implementation details |
| **Researchers** | 2, 5, 8 | Algorithms, comparisons |
| **Skeptics** | 1, 4, 8 | Defense of design choices |

---

## Connection-Sparking Exercises

### Cross-Section Connections

After mastering individual sections, draw connections:

1. **Philosophy → Implementation:** How does "games as first-class citizens" (Section 1) manifest in the parser (Section 7)?

2. **Game Theory → Syntax:** Why does understanding Nash equilibrium (Section 2) inform the design of `solve` (Section 3)?

3. **Refusals → Comparisons:** How do Tenet's explicit rejections (Section 4) create advantages over Gambit (Section 8)?

4. **Models → Stack:** How does modeling the Tragedy of the Commons (Section 6) benefit from Flux integration (Section 9)?

5. **Solver → Philosophy:** How do computational limits of Nash (Section 5) validate "narrow by design" (Section 1)?

### Deep Questions About Questions

1. Which question in this document can you not answer? That's your biggest gap.
2. Which question seems obvious but has a subtle answer? Those reveal mastery.
3. Which question would you add that's not here? That shows you've gone beyond.
4. If you could only memorize 10 answers, which 10? Why those?
5. What question would make you sweat in front of a VC? Master that one first.

---

**If you can answer all 300 of these under pressure, no VC, no interviewer, no skeptic can shake you.**

*Built for the architect of Tenet. If you didn't build it, you can't fake it.*

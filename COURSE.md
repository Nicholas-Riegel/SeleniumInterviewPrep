# Selenium + Java — 7-Day Interview Prep Course

## Note to Copilot (read this first, before doing anything else)

**Student**
- ~2 years QA experience (manual + some automation). Prior tool: Laravel Dusk (locators/clicking concepts already familiar).
- Java: intermediate (classes, inheritance, interfaces). Do NOT explain variables, loops, etc.
- Has already done this course once months ago (see `reference/old-course/`) but forgot most of it. This is a **re-teach from scratch**.
- **Goal:** pass a second-round technical interview for a QA role (Java + Selenium, ~2 years experience level) in about a week (started Mon 2026-10-12, course ends Sun 2026-10-18, Mon 2026-10-19 is review). Format is unknown but a take-home is unlikely: expect spoken "explain how you'd..." questions, live-coding Selenium, and plain Java problems (collections, strings, OOP). Prepare for all of them.
- **Time budget:** at least 4 hours/day.

**Working style (follow strictly)**
- Explain what you plan to do and get explicit approval before doing it.
- The student does all hands-on work (creating files, typing code, running commands). Give exact commands/content, but do not create or edit the student's source files or run their tests unless asked.
- Explain every concept before any code. No unexplained copy-pasting. Every line gets explained.
- Teach one section at a time. Finish it before moving on.
- Each module ends with a problem set written as challenges (no code given upfront). Show solutions only after the student attempts them.
- Every day ends with 5–8 spoken-style interview questions on that day's material. Student answers first, then Copilot corrects.
- Every day starts with a 45-minute Java block (before Selenium, so it never gets skipped). Focus: plain-Java QA-level questions (collections, strings, OOP, exceptions, basic streams), not hard algorithms.
- Start from `reference/` files and have Copilot explain them rather than typing everything from scratch. Teach gaps at the student's pace (see Pacing).
- **Pacing (student's explicit request): ONE small step per message.** Never give multiple commands, concepts, or tasks in one reply. Explain what each command does and why BEFORE asking the student to run it. No bundled "check questions" or quizzes unless the student asks for them or it's the scheduled Q&A time; the student will say when to ask questions.
- Be concise. Keep a running one-page cheat sheet in `CHEATSHEET.md` (student writes it, Copilot reviews).

**Environment**
- macOS. Maven 3.9.10. Projects target **Java 21** (SDKMAN `21.0.7-amzn`), now the global default; verify with `mvn -v`. Allure was dropped from the course.
- Google Chrome is installed.
- Selenium 4.20.0, TestNG 7.10.2, AssertJ 3.25.3, Allure 2.27.0, Surefire 3.2.5 (see `reference/pom.xml`).
- Run tests in the terminal: `mvn test` or `mvn test -Dtest=ClassName`.
- Test sites: saucedemo.com (login/products/cart), demoqa.com (forms, alerts, iframes, drag-and-drop), the-internet.herokuapp.com (uploads, hovers, dynamic content).
- This is a SERIES of small Maven projects, one per phase, each in its own subfolder of the repo root (student's choice, so topics don't get mixed): `01-fundamentals` (Days 1–2), `02-pom` (Day 3), `03-testng` (Day 4), `04-advanced` (Day 5), Day 7 simulation in a fresh project. Create each project only when its phase starts. Java drills (Day 6) can be plain files.
- Java is Homebrew-free: SDKMAN default is `21.0.7-amzn` (the Java 25 lines in `~/.zshrc` were commented out).
- Java practice follows the same rule: one small Maven project per topic, created by the student (not one shared project). `java-practice` = interfaces (Shapes/Circle/Rectangle), finished and committed; do not modify it. Next: a new project for abstract classes. Java practice projects use the standard `src/main/java` layout (VS Code shows no errors for loose files or a `sourceDirectory` override). The student prefers writing real code from challenges over answering questions.

**Reference material (look things up, do not re-read it all)**
- `reference/pom.xml`, `reference/testng.xml`: known-good config.
- `reference/workflows/selenium.yml`: working GitHub Actions pipeline.
- `reference/old-course/SeleniumNotes.md` (2,500 lines, archive) and `SeleniumProblemSets.md` (reusable challenges).

---

## Progress

- [ ] Day 1 (Mon, short evening): Toolchain, WebDriver model, project setup, first test, Java diagnostic
- [ ] Day 2 (Tue): Locators, interactions, waits
- [ ] Day 3 (Wed): Page Object Model + BaseTest
- [ ] Day 4 (Thu): TestNG + AssertJ + parallel/ThreadLocal
- [ ] Day 5 (Fri): Actions, JS Executor, windows, screenshots, headless, CI walkthrough
- [ ] Day 6 (Sat): Java coding drill, QA fundamentals, Grid/BDD concepts, framework-design talk
- [ ] Day 7 (Sun): Live-coding simulation, mock interview, finalize cheat sheet
- [ ] Mon 19th: Review only

**Current position (end of Tue 2026-10-13):** Day 1 done (s01_fundamentals: first passing test with AssertJ assertion). Java diagnostic done. Tuesday was spent on Java OOP instead of Selenium (student had facial surgery that day, in pain and tired, so keep Wednesday's pace gentle): `java-practice/j01_interfaces` (interface + polymorphism, finished) and `j02_abstract_classes` (abstract class with shared field/constructor/method via `super(...)`, finished; Circle's `area()` is currently commented out in the student's experiment, so it doesn't compile until restored). The OOP block planned for Day 3's Java slot is mostly done. Known gaps still to revisit: String `==` vs `equals` (shaky), overloading vs. overriding, exceptions. The student's VS Code often doesn't show Java errors for new modules; `mvn compile` is the authority. We are about one day behind: Selenium locators (Day 2) start Wednesday, one small step at a time.

**Daily template (4 hours):** Java 45 min → Selenium teach + hands-on ~2h15 → problem set 30 min → spoken interview Q&A 30 min. Day 1 is a shortened version of this.

**Dropped on purpose (mention conceptually only):** Excel/Apache POI, Allure/ExtentReports, building CI from scratch, hard algorithms (merge sorted lists etc., optional).

---

## Day 1 — Setup and WebDriver Model (short evening)
- [ ] Toolchain check: `java -version`, `mvn -v`, Chrome installed
- [ ] How WebDriver works: Java client → W3C WebDriver protocol (HTTP/JSON) → ChromeDriver → Chrome. Selenium Manager downloads the driver.
- [ ] Create the Maven project from `reference/pom.xml` (Copilot explains each part; student types it)
- [ ] First test: open a page, assert the title, quit the driver
- [ ] Java diagnostic: a few quick questions/snippets (collections, String, OOP) to find weak spots
- [ ] Interview Q&A: how does Selenium work end to end

## Day 2 — Locators, Interactions and Waits
- [ ] Java (45 min): collections (List/Set/Map), String methods
- [ ] Locators: id, name, class, tag, link text; CSS selectors (priority); XPath (when needed); Selenium 4 relative locators; DevTools verification; fragile vs. reliable locators
- [ ] click, sendKeys, clear, getText, getAttribute, isDisplayed/isEnabled/isSelected
- [ ] `Select` dropdowns, alerts, iframes, file upload
- [ ] Why tests flake; implicit vs. explicit vs. fluent waits (never mix implicit and explicit); `WebDriverWait` + `ExpectedConditions`; the conditions used daily
- [ ] Problem set: locator challenges (saucedemo, the-internet) + waits against dynamic-loading pages
- [ ] Interview Q&A: locator strategy, CSS vs. XPath, StaleElementReferenceException, ElementNotInteractable, implicit vs. explicit

## Day 3 — Page Object Model
- [ ] Java (45 min): OOP (inheritance, interfaces, abstract vs. interface, overloading vs. overriding), exceptions (checked vs. unchecked, try/catch/finally)
- [ ] Why POM exists; `BasePage` with shared helpers (wait + click, type, getText)
- [ ] Page classes: private locators, expressive methods, return page objects
- [ ] `BaseTest` for driver lifecycle (`@BeforeMethod`/`@AfterMethod`, headless switch)
- [ ] Build: LoginPage, ProductsPage, CartPage on saucedemo + tests
- [ ] Interview Q&A: explain POM, PageFactory vs. plain POM, how to structure a framework

## Day 4 — TestNG and AssertJ
- [ ] Java (45 min): String and array problems (reverse, palindrome, anagram, duplicates, frequency count with HashMap); `equals`/`hashCode`, String immutability
- [ ] Annotations and lifecycle order (`@BeforeSuite` … `@AfterSuite`), `@Test` attributes (priority, groups, dependsOnMethods, enabled)
- [ ] `@DataProvider` (inline), `testng.xml` suites, groups, parallel execution + `ThreadLocal<WebDriver>`
- [ ] TestNG assertions vs. AssertJ; hard vs. soft assertions
- [ ] Problem set: suite with groups and a parallel run
- [ ] Interview Q&A: why ThreadLocal, `@BeforeClass` vs. `@BeforeMethod`, soft asserts

## Day 5 — Advanced Interactions, Headless and CI
- [ ] Java (45 min): streams and lambdas, Optional, generics basics
- [ ] `Actions`: hover, right-click, drag-and-drop, key combos
- [ ] `JavascriptExecutor`: scrollIntoView, JS click, reading DOM values; the casting pattern
- [ ] Multiple windows/tabs, screenshots on failure (`ITestListener` or `@AfterMethod` with `ITestResult`)
- [ ] Headless Chrome, Surefire with `testng.xml`, `mvn test` flags and system properties
- [ ] CI walkthrough: read and explain `reference/workflows/selenium.yml` (not built from scratch)
- [ ] Interview Q&A: when to use JS click, how to handle new windows, how do you run tests in CI

## Day 6 — Java Drill, QA Fundamentals and Concepts
- [ ] Java coding drill (timed, ~60 min): FizzBuzz, two-sum, balanced brackets, first non-repeating char, word count with streams; Java 21 basics (records, `var`)
- [ ] QA fundamentals (~20 min): test case design, writing a good bug report, test pyramid, what to automate vs. keep manual, smoke vs. regression, REST/status codes and API testing basics (Postman vs. RestAssured)
- [ ] Concepts, ~15 min each: Selenium Grid / `RemoteWebDriver`, Shadow DOM, what Cucumber/BDD is, test data management, Laravel Dusk background and how it transfers
- [ ] Framework-design talk: structure, reporting, flaky-test handling
- [ ] Scenario questions: "passes locally, fails in CI", "how do you debug a flaky test", "how would you start automating this app"
- [ ] Interview Q&A: mixed spoken round

## Day 7 — Live-Coding Simulation and Mock Interview
- [ ] Live-coding simulation (60–90 min): automate login → add to cart → checkout on saucedemo with POM, TestNG, assertions, narrating choices aloud
- [ ] Mock interview: mixed Selenium + Java + QA questions, Copilot plays interviewer
- [ ] Finalize `CHEATSHEET.md`
- [ ] Flag weak spots for Monday review

---

## Interview "must be able to say" list (tick off as mastered)
- [ ] How Selenium works end to end (client, protocol, driver, browser)
- [ ] Preferred locator order and why; CSS vs. XPath
- [ ] Implicit vs. explicit vs. fluent waits; why not to mix
- [ ] StaleElementReference, NoSuchElement, ElementNotInteractable: cause and fix
- [ ] What POM is and how I structured mine
- [ ] TestNG lifecycle, DataProvider, groups, parallel execution + ThreadLocal
- [ ] When to use `Actions` or JS Executor instead of normal commands
- [ ] How I handle iframes, alerts, windows
- [ ] How tests run in CI, headless, and how I report
- [ ] How I'd handle flaky tests
- [ ] Core Java: `equals`/`hashCode`, String immutability, checked vs. unchecked, overloading vs. overriding
- [ ] QA basics: good bug report, test pyramid, what to automate

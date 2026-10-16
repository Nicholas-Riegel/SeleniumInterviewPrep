# Selenium + Java — 7-Day Interview Prep Course

## Note to Copilot (read this first, before doing anything else)

**Student**
- ~2 years QA experience (manual + some automation). Prior tool: Laravel Dusk (locators/clicking concepts already familiar).
- Java: intermediate (classes, inheritance, interfaces). Do NOT explain variables, loops, etc.
- Has already done this course once months ago (see `reference/old-course/`) but forgot most of it. This is a **re-teach from scratch**.
- **Goal:** pass a second-round technical interview for a QA role (Java + Selenium, ~2 years experience level) in about a week (started Mon 2026-10-12, course ends Sun 2026-10-18, Mon 2026-10-19 is review). Format is unknown but a take-home is unlikely: expect spoken "explain how you'd..." questions, live-coding Selenium, and plain Java problems (collections, strings, OOP). Prepare for all of them.
- **Time budget:** at least 4 hours/day.

**Working style (follow strictly)**
The student's explicit feedback (Wed 2026-10-14): the first course (`SeleniumSyllabus.md`, notes in `reference/old-course/`) went much better than the tiny-step, challenge-first style used on Days 1–2. Teach the way the first course did.

- Explain what you plan to do and get explicit approval before doing it (once per section or module, not per step).
- The student does all hands-on work (creating files, typing code, running commands). Give exact commands/content, but do not create or edit the student's source files or run their tests unless asked.
- **Teaching loop, one SECTION per message:** (1) explain the concept, (2) show the complete working code and explain every line, (3) give the exact command to run, (4) end with 3–5 "key points" for interviews. The student types and runs the example before any challenge. No unexplained copy-pasting. No single-step drip-feeding.
- Teach one section at a time. Finish it before moving on.
- **Problem sets come at the END of a module,** not in the middle of a lesson. Challenges name the methods, classes and imports to use and what each does, but give no finished code. If the student asks for the code or says they are lost, give the solution immediately, with no further hints and no "try again".
- When an instruction could be read two ways (e.g. whether a step includes a click), state the goal first and mark the exact line to write. If an instruction turns out to be unclear, say so and fix it.
- Do not ask for "it passed" reports. The student pastes only failures or surprises.
- Every day ends with 5–8 spoken-style interview questions on that day's material. Student answers first, then Copilot corrects.
- **Java review is kept** (student likes it): each day includes a 45-minute plain-Java block (collections, strings, OOP, exceptions, basic streams, not hard algorithms). Run it as a worked lesson in the same style (concept, code, key points), then a short challenge. It may be moved to the end of the day if the student prefers to start with Selenium.
- Start from `reference/` files and have Copilot explain them rather than typing everything from scratch.
- **Notes:** the student maintains `Selenium_Notes.md`, `Java_Notes.md` and `CHEATSHEET.md` themselves. Do not write them. End each section with key points the student can copy. Review the cheat sheet when asked.
- Be concise.

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

- [x] Day 1 (Mon, short evening): Toolchain, WebDriver model, project setup, first test, Java diagnostic
- [x] Day 2 (Wed): Locators, interactions, waits (problem set dropped by student's choice; Q&A folded into Thursday evening)
- [x] Day 3 + Day 4 combined (Thu, carried into Fri 10-16): all hands-on work done (`s03_pom` = POM snapshot, `s04_testng` = TestNG work incl. lifecycle demo). Only the Q&A is open (moved to Sat/Sun)
- [x] Day 5 (Fri): all hands-on work done (Actions, JS Executor, windows, screenshots, headless, CI walkthrough). Still open: the Day 5 Q&A and the deferred Java review blocks (Sat/Sun)
- [ ] Day 6 (Sat 10-17): drills: 1) locators, 2) find and add Maven dependencies yourself, then set up and run multithreaded POM tests, 3) Java skills review, 4) Grid and BDD concepts (short, spoken)
- [ ] Day 7 (Sun 10-18): drills: 1) more Java review, 2) Actions, JS Executor, windows/tabs, 3) QA fundamentals chat, 4) live-coding simulation. Mock interview and cheat sheet are unscheduled (see Day 7)
- [ ] Mon 19th: Review only (plus anything still unfinished)

**Plan for Thursday 2026-10-15 (student's decision, both modules are required to get back on track):** do Day 3 (POM) and Day 4 (TestNG + AssertJ + parallel/ThreadLocal) in one day. No locator problem set. Teach tight as a **review-and-rebuild**, since the student already built all of this in the first course, in `/Users/nicholas/Dev/Selenium/SeleniumSelfStudy` (sibling folder, read-only reference for us; do not modify it):
- `selenium-pom/`: `BasePage` (protected `driver` and `wait`, `click`/`type`/`getText` with built-in waits; the comment block explains why driver lifecycle does NOT belong in BasePage), `LoginPage` (private `final By` locators, `loginAs(...)` returns `ProductsPage`, `getErrorMessage()`), `ProductsPage` (`isLoaded()`), `MultiThreadLoginTest` (`ThreadLocal<WebDriver>`, `@BeforeMethod(alwaysRun = true)`/`@AfterMethod` with `remove()`, groups `smoke`/`regression`, parallel `@DataProvider`, AssertJ), `testng.xml` (`parallel="methods" thread-count="3"`).
- `selenium-ci/src/test/java/com/seleniumstudy/base/BaseTest.java`: `BaseTest` with the `HEADLESS` env-var switch.
- Not covered there, so teach these as NEW: `CartPage` and the add-to-cart flow, a proper reusable `BaseTest` that holds the `ThreadLocal<WebDriver>`, AssertJ soft assertions (`SoftAssertions`), `@Test` attributes (`priority`, `dependsOnMethods`, `enabled`), lifecycle order (`@BeforeSuite` … `@AfterSuite`) and `@BeforeClass` vs. `@BeforeMethod`, PageFactory vs. plain POM (conceptual), and the tradeoff "why ThreadLocal".

Order: (1) go straight to rebuilding, with NO read-and-explain step (student's decision): new project `s02_pom` in this repo, student re-types/adapts `BasePage`, `LoginPage`, `ProductsPage` quickly, using the first-course files above as the source to copy from; (2) add `CartPage` and a `BaseTest` with `ThreadLocal` and a headless switch; (3) tests: login, add to cart, cart badge/contents, with soft assertions; (4) groups, `testng.xml`, parallel run, `-Dgroups=` runs; (5) lifecycle order demo and `@Test` attributes. No Java block, no end-of-module problem sets (the tests are the exercise). End the day with the spoken Q&A, mixing a few Day 2 questions (locator strategy, CSS vs. XPath, StaleElementReference, implicit vs. explicit waits) plus POM and TestNG questions. If time runs short, the first thing to move to Friday is step (5).

**Update (Fri 2026-10-16, morning):** The student rebuilt everything in **`s03_pom`** (not `s02_pom`; `s02_pom` is an abandoned first attempt, ignore it). Student preference confirmed: do NOT hand over finished code for rebuild tasks; give the concept plus a spec of what the class needs, and give code only if asked or stuck. Ask one question at a time.
- Done and passing (5 tests, headless + incognito): `BasePage`, `LoginPage`, `ProductsPage` (`isLoaded`, `addItemToCart(String)` is `void` and scoped to the matching card with an early `return`, `goToCart()` returns `CartPage`), `CartPage` (`itemIsInCart`, locator `.cart_item`), `LoginPageTest` (2 tests), `ProductsPageTest` (`@DataProvider` with `Object[][]`, `parallel = true`).
- `ThreadLocal<WebDriver>` already exists, but it is **copy-pasted into both test classes** as an instance field, with `ChromeOptions` (`--incognito`, `--headless=new` hardcoded). `tearDown()` calls `quit()` but **not `driver.remove()`**.
- `testng.xml` exists (`parallel="methods" thread-count="3"`, both classes) but is **not wired into the POM**: there is no Surefire `suiteXmlFiles`, so `mvn test` ignores it. No test has `groups` yet, so the `-Dgroups=smoke` comments in it do not work yet.
- Not started: `BaseTest` (extract the duplicated setup, `HEADLESS` env switch, `remove()`), `SoftAssertions`, cart badge check, groups, wiring `testng.xml`, lifecycle order demo, `@Test` attributes (`priority`, `dependsOnMethods`, `enabled`), the spoken Q&A. Student also dislikes the SLF4J and CDP-version console warnings (harmless; the CDP one comes from Chrome 155 being newer than Selenium 4.20.0). Possible cleanup: `slf4j-nop` test dependency matching the `slf4j-api` version, and/or lowering the `org.openqa.selenium` logger level in `BaseTest`.
- `BaseTest` is done (Fri 10:00): `ThreadLocal` plus `getDriver()`, `remove()`, `HEADLESS` env switch, `alwaysRun = true`; both test classes extend it. Student is making `BasePage`/`BaseTest` abstract.
- Plan for Friday (student's decision, 10:07): soft assertions + groups + wired `testng.xml` (60 min), lifecycle/`@Test` attributes (30 min), then all of Day 5 (Actions, JS Executor, windows, screenshots, headless, CI walkthrough). **The spoken Q&A (Days 2–5) and the deferred Java review blocks are moved to Saturday and Sunday** (Saturday's mixed Q&A round must cover Day 2–5 questions; Java blocks join the Saturday drill).

**Update (Fri 2026-10-16, 11:25):** `s04_testng` is green: `mvn test` = 5 tests, `-Dgroups=smoke` = 4, `-Dgroups=regression` = 4, suite name "Saucedemo Suite" (so `testng.xml` is wired). Soft assertions, groups and `getCartBadgeCount()` done. **Next (afternoon):** lifecycle order demo + `@Test` attributes (`priority`, `dependsOnMethods`, `enabled`), then Day 5: `Actions`, `JavascriptExecutor`, windows/tabs, screenshots on failure, headless/CI walkthrough (`reference/workflows/selenium.yml`). Q&A and Java blocks stay on Sat/Sun.

**Update (Fri 2026-10-16, 16:55):** Day 5 is in `s05_advanced` (copy of `s04_testng`, no `testng.xml`, `BaseTest` no longer opens a page, each test calls `get(...)` on the-internet): `ActionsTest`, `JSExecuterTest`, `WindowsTest` all passing. **Student rule (reconfirmed 16:07/16:20):** for NEW material, show the code in chat with a line-by-line explanation and let the student paste it in and run it. Do NOT create or edit the student's test files unless asked. **One test/step per message.** Remaining today: screenshots on failure, then the headless/CI walkthrough (read `reference/workflows/selenium.yml`, can slip to Saturday morning). Q&A and Java blocks stay on Sat/Sun.

**Update (Fri 2026-10-16, 18:35):** Days 1–5 hands-on work is complete. Student's plan for the weekend: **Saturday** = drill locators, drill setting up/running multithreaded POM tests, Java skills review. **Sunday** = more Java review, drill `Actions`/`JavascriptExecutor`/windows. The original Day 6/7 items (QA fundamentals, Grid/BDD concepts, live-coding simulation, mock interview, cheat sheet) are no longer scheduled; they are listed under Day 7 as "not scheduled" so nothing is silently lost. Mention this to the student if time looks short on Sunday.

**Deferred Java review (student wants to keep it):** Day 2's block (collections List/Set/Map, String methods, `==` vs `equals`), Day 3's block (overloading vs. overriding, exceptions) and Day 4's block (String/array problems, `equals`/`hashCode`, immutability) move to Friday (45 min at the start) and the Saturday drill. Do them as worked lessons in the usual style.

**Update (Wed 2026-10-14, evening):** Day 2 hands-on work is done in `s01_fundamentals`: `LocatorsTest` (id, name, class, tag, linkText, findElements, CSS incl. attribute/partial/combinators, XPath incl. text and parent/sibling, relative locators), `InteractionsTest` (login, clear, checkboxes, `Select`, alerts, nested iframes, file upload, with a wait added to the upload test), and `WaitsTest` (explicit wait, timeout with `TimeoutException`, `FluentWait`). Locator priority was discussed. The student is still recovering from surgery, so keep sections short and clear. Run `mvn test` once in `s01_fundamentals` at the start of Thursday to confirm everything is green (the-internet is sometimes slow).

**Previous position (end of Tue 2026-10-13):** Day 1 done (s01_fundamentals: first passing test with AssertJ assertion). Java diagnostic done. Tuesday was spent on Java OOP instead of Selenium (student had facial surgery that day, in pain and tired, so keep Wednesday's pace gentle): `java-practice/j01_interfaces` (interface + polymorphism, finished) and `j02_abstract_classes` (abstract class with shared field/constructor/method via `super(...)`, finished; Circle's `area()` is currently commented out in the student's experiment, so it doesn't compile until restored). The OOP block planned for Day 3's Java slot is mostly done. Known gaps still to revisit: String `==` vs `equals` (shaky), overloading vs. overriding, exceptions. The student's VS Code often doesn't show Java errors for new modules; `mvn compile` is the authority. We are about one day behind: Selenium locators (Day 2) start Wednesday, one small step at a time.

**Daily template (4 hours):** Java 45 min (worked lesson + short challenge) → Selenium sections (concept, full code, run, key points) ~2h15 → end-of-module problem set 30 min → spoken interview Q&A 30 min. Day 1 was a shortened version of this.

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
- [ ] Java (45 min): collections (List/Set/Map), String methods. **Deferred to Friday.**
- [x] Locators: id, name, class, tag, link text; CSS selectors (priority); XPath (when needed); Selenium 4 relative locators; DevTools verification; fragile vs. reliable locators
- [x] click, sendKeys, clear, getText, getAttribute, isDisplayed/isEnabled/isSelected
- [x] `Select` dropdowns, alerts, iframes, file upload
- [x] Why tests flake; implicit vs. explicit vs. fluent waits (never mix implicit and explicit); `WebDriverWait` + `ExpectedConditions`; the conditions used daily
- [ ] ~~Problem set: locator challenges + waits~~ (dropped by student's choice)
- [ ] Interview Q&A: locator strategy, CSS vs. XPath, StaleElementReferenceException, ElementNotInteractable, implicit vs. explicit. **Moved to the end of Thursday.**

## Day 3 — Page Object Model (Thursday, combined with Day 4)
- [ ] Java (45 min): OOP (inheritance, interfaces, abstract vs. interface, overloading vs. overriding), exceptions (checked vs. unchecked, try/catch/finally). **Skipped Thursday; OOP mostly done Tuesday, rest deferred to Friday.**
- [x] Why POM exists; `BasePage` with shared helpers (wait + click, type, getText)
- [x] Page classes: private locators, expressive methods, return page objects
- [x] `BaseTest` for driver lifecycle (`@BeforeMethod`/`@AfterMethod`, headless switch, `ThreadLocal`, `remove()`, `alwaysRun`); abstract
- [x] Build: LoginPage, ProductsPage, CartPage on saucedemo + tests (login, add to cart, cart badge via `getCartBadgeCount()` in `ProductsPage`); done in `s04_testng`, with `s03_pom` kept as the clean POM snapshot
- [ ] Interview Q&A: explain POM, PageFactory vs. plain POM, how to structure a framework

## Day 4 — TestNG and AssertJ (Thursday, after Day 3's POM)
- [ ] Java (45 min): String and array problems (reverse, palindrome, anagram, duplicates, frequency count with HashMap); `equals`/`hashCode`, String immutability. **Deferred to Friday/Saturday.**
- [x] Annotations and lifecycle order (`@BeforeSuite` … `@AfterSuite`), `@Test` attributes (priority, groups, dependsOnMethods, enabled); done with `LifecycleDemoTest` in `s04_testng` (run with `mvn test -Dtest=LifecycleDemoTest`, since it is not in `testng.xml`)
- [x] `@DataProvider` (inline, `Object[][]`)
- [x] `testng.xml` suite wired through Surefire 3.2.5 `suiteXmlFiles` in `s04_testng` (suite "Saucedemo Suite" confirmed in `testng-results.xml`), groups `smoke`/`regression` added (`mvn test` = 5, `-Dgroups=smoke` = 4, `-Dgroups=regression` = 4), parallel execution (`parallel = true` data provider + suite-level `parallel="methods"`, both with `ThreadLocal<WebDriver>`)
- [x] TestNG assertions vs. AssertJ; hard vs. soft assertions (`SoftAssertions` as a local variable in `itemIsAddedToCart`; a shared field would race under parallel; `assertSoftly` or a `ThreadLocal` are the alternatives)
- [x] Problem set: suite with groups and a parallel run (done via `s04_testng`)
- [ ] Interview Q&A: why ThreadLocal, `@BeforeClass` vs. `@BeforeMethod`, soft asserts

## Day 5 — Advanced Interactions, Headless and CI
- [ ] Java (45 min): streams and lambdas, Optional, generics basics
- [x] `Actions`: hover, right-click, key presses and key combos done in `s05_advanced` (`ActionsTest`); drag-and-drop covered as concept only (HTML5 drag events are unreliable with `Actions`, JS fallback)
- [x] `JavascriptExecutor`: reading `document.title`, scrollIntoView, JS click, the `(JavascriptExecutor)` cast and `Long` return type; done in `JSExecuterTest`
- [x] Multiple windows/tabs (`WindowsTest`: original handle, wait for 2 windows, loop `getWindowHandles()`, `switchTo().window`, `close()` vs `quit()`)
- [x] Screenshots on failure: manual version in `@AfterMethod` with `ITestResult` first, then the `ITestListener` version (`TestListener.onTestFailure`, registered with `@Listeners` on `BaseTest`, saves to `target/screenshots/`); both seen working in `s05_advanced`
- [x] Headless Chrome (`HEADLESS=true mvn test` via `System.getenv` in `BaseTest`), Surefire with `testng.xml` (done in `s04_testng`), `mvn test` flags (`-Dtest=`, `-Dgroups=`)
- [x] CI walkthrough: read `reference/workflows/selenium.yml` (GitHub Actions: trigger on push/PR, fresh Ubuntu VM, checkout, Java 21, `HEADLESS=true mvn test`, upload artifacts with `if: always()`). Student's summary: "instructions to set up a virtual machine and run the tests in the cloud". CI is understood only at a basic level; revisit briefly in Saturday's Q&A (why headless in CI, "passes locally, fails in CI").
- [ ] Interview Q&A: when to use JS click, how to handle new windows, how do you run tests in CI

## Day 6 — Saturday 2026-10-17: Drills (student's plan, set Fri 18:35)
Format: drill and practice, not new teaching. For each topic the student builds from memory; Copilot gives concept + spec and hints, not finished code (see the student's rules above).
- [ ] 1. Locators drill: id, name, class, tag, linkText, CSS (attribute, partial, combinators), XPath (text, parent/sibling), relative locators; CSS vs. XPath and locator priority. Practice target: saucedemo (the-internet is OK when fast)
- [ ] 2a. Maven dependencies: find and add them yourself (the student has let Copilot supply `pom.xml` until now and fears being asked in the interview). Cover: what `pom.xml` is, the `groupId`/`artifactId`/`version` coordinates, searching https://central.sonatype.com or https://mvnrepository.com for `selenium-java`, `testng` and `assertj-core`, copying the `<dependency>` block, `<scope>test</scope>` and why, the `<properties>` Java version, the Surefire plugin and `suiteXmlFiles`, running `mvn compile`/`mvn test`, and reading a dependency error (artifact not found, wrong version, missing scope). Drill: student builds a new `pom.xml` for an empty project without help. Reference: `s04_testng/pom.xml`
- [ ] 2. Set up and run multithreaded POM tests from scratch: `BasePage`, page classes, abstract `BaseTest` with `ThreadLocal<WebDriver>` (`remove()`, `alwaysRun`, `HEADLESS`), `@DataProvider(parallel = true)`, `testng.xml` with `parallel="methods"` wired through Surefire (`suiteXmlFiles`), groups and `-Dgroups=`. Reference solutions: `s03_pom` (POM snapshot) and `s04_testng` (TestNG)
- [ ] 3. Java skills review: collections (List/Set/Map), String methods and immutability, `==` vs `equals`, `equals`/`hashCode`, overloading vs. overriding, exceptions (checked vs. unchecked, try/catch/finally); worked lessons plus short challenges (the deferred Java blocks from Days 2–4)
- [ ] 4. Concepts, short (about 5 min each, spoken, no building): Selenium Grid / `RemoteWebDriver` (hub, nodes, `RemoteWebDriver` pointed at the hub URL, cloud grids like BrowserStack/Sauce Labs, how it scales parallel runs) and BDD / Cucumber (Gherkin Given/When/Then, step definitions, when it helps; be honest if not used). Check the job description first: if it names Cucumber or Grid, give them more time
- [ ] Spoken Q&A on today's topics (locators, ThreadLocal, `@BeforeClass` vs. `@BeforeMethod`, soft asserts, why headless in CI), as time allows

## Day 7 — Sunday 2026-10-18: Drills (student's plan, set Fri 18:35)
- [ ] 1. More Java review: streams and lambdas, Optional, generics basics, String/array problems (reverse, palindrome, anagram, duplicates, frequency count with HashMap), `equals`/`hashCode` again if still shaky
- [ ] 2. Drill `Actions` (hover, right-click, key presses/combos), `JavascriptExecutor` (read, scroll, JS click, casting), windows/tabs (handles, switch, `close()` vs. `quit()`); build from memory in a fresh project, reference: `s05_advanced`
- [ ] 3. QA fundamentals chat (about 20 min, spoken): test case design, writing a good bug report, test pyramid, what to automate vs. keep manual, smoke vs. regression, REST/status codes and API testing basics (Postman vs. RestAssured)
- [ ] 4. Live-coding simulation (60-90 min, afternoon): automate login -> add to cart -> checkout on saucedemo with POM, TestNG and assertions, narrating choices aloud. Student builds it from scratch, including the `pom.xml`; Copilot plays interviewer and only gives hints when asked
- [ ] Spoken Q&A for those topics (when to use JS click, how to handle new windows, screenshots on failure, how tests run in CI), as time allows

**Not scheduled after the student's plan (decide Sunday evening or fold into Monday review):** concepts (Shadow DOM, test data management, Laravel Dusk transfer; Grid and BDD moved to Saturday), framework-design talk and scenario questions ("passes locally, fails in CI", "debug a flaky test"), the mock interview, finalizing `CHEATSHEET.md`, flagging weak spots.

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

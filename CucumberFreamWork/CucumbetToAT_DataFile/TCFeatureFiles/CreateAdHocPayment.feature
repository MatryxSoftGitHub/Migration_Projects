#Author: Nagarjuna
#Create Ad Hoc Payment on Accounts Payable Settlement Specialist and take approval fro Treasury Administrator. 

#User login workday application with user name lkumar.
#Background:

    
Feature: Create Ad Hoc Payment
  Create Ad Hoc Payment on Accounts Payable Settlement Specialist
  and take approval fro Treasury Administrator. 

Scenario Outline: Create ad hoc payment
    Given user is on login page
    Then user should get Workday transre_preview title page
    | PageTitle |
    | Workday transre_preview |
    When user enters username and password
    |userName|password|
    |lkumar|Matryx1@2020|
    And user click on sign in button
    Then user gets Remember Divice message
    And user click on skip button
    Then user should get title of the home page
    
    Given user search for Start Proxy in the Search bar and  user click on Start proxy link
    |StartProxy|
    |Start Proxy|
    When Enter in the Act As Alice Chow
    |ActAs|
    |Alice Chow|
    And  click OK
    When Search Create Ad Hoc Payment in the search bar
    |PaymentType|
    |Create Ad Hoc Payment|
    And  user click on Create Ad Hoc Payment link
    
    Given user enters values for Ad Hoc Payment Information "<Company>" and "<Bank Account>" and "<Payee>" and "<Currency>" and "<Payment Type>" and "<Ship-To Address>" and "<Hand ling Code>" 
    And  user enters values for Payment Details "<Control Total Amount>" and "<Freight Amount>" and "<Other Charges>" and "<Memo>" 
    When user enters values for Lines table "<Lines Company>" and "<Spend Category>" and "<Tax Applicability>" and "<Quantity>" and "<Unit Cost>" and "<LinesMemo>" and "<Lines Cost Center>" and "<Additional Worktags>" 

    Examples: 
      |Company                        |Bank Account                 |Payee        |Currency|Payment Type|Ship-To Address                                                             |Handling Code|Control Total Amount|Freight Amount|Other Charges|Memo|Lines Company                  |Spend Category|Tax Applicability|Quantity|Unit Cost|LinesMemo|Lines Cost Center       |Additional Worktags|
      |NYT USD  - New York Home Office|NYT Operating - JPMC USD-4785|EVP I LP Mgmt|USD     |Wire        |One Liberty Plaza - 165 Broadway New York, NY 10006 United States of America|Immediate    |400                 |0             |0            |Memo|NYT USD  - New York Home Office|Advertising   |Sales/Use Tax    |1       |400      |Memo     |100-000 General Overhead|Customer: Farmers  | 






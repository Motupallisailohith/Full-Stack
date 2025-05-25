# 📄 ClausePilot – Contract Intelligence Platform

ClausePilot is a modular document intelligence system that extracts, classifies, and interprets clauses from complex documents such as insurance policies, loan agreements, and legal contracts — all personalized to the user's context.

This project is being built to showcase advanced system design, document AI integration, and modern software engineering practices across microservices, with clear documentation, testing strategy, and extensibility.

---

## 🧠 Core Objectives

- 🧾 Parse contracts into structured clauses
- 🧠 Extract both atomic values and full legal clauses (e.g., “Death Benefit”)
- 🤖 Choose between rule-based and ML-based (Google Document AI) parsers
- 📌 Link contract data with user profile context (age, health, plan)
- 💬 Provide personalized advice based on incidents or questions
- ⚙️ Operate via a distributed, scalable microservice architecture

---

## 🧩 Microservices Overview

| Microservice               | Role |
|----------------------------|------|
| `contract-parser-service`  | Upload + parse contracts using Tika/Document AI |
| `clause-classifier-service`| Classify extracted clauses (refund, grace period, exclusions) |
| `user-service`             | Manages user profile (age, income, plan) |
| `query-advisor-service`    | Accepts user incidents/questions and maps them to clauses |
| `risk-advisor-service`     | Evaluates clause impact + flags user risks or suggestions |
| `event-trigger-service`    | Tracks clause-based dates (e.g. expiration, refund window) |
| `workflow-service`         | Executes system actions (alerts, escalations, reminders) |
| `notification-service`     | Sends email/SMS/push alerts |
| `access-control-service`   | Manages org-based and role-based contract visibility |
| `reporting-service`        | Generates clause summaries and audit reports (PDFs) |
| `frontend-ui`              | React frontend to upload, view, query, and receive alerts |

---

## 🚀 Quick Start (for Developers)

```bash
# Navigate to a service folder
cd contract-parser-service

# Set your Google service account key for Document AI
set GOOGLE_APPLICATION_CREDENTIALS=C:\path\to\your-key.json

# Start the service
mvn spring-boot:run

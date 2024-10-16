val author = Document("_id", ObjectId())
    .append("name", "Gabriel García Márquez")
    .append(
        "dateOfDeath",
        Date.from(
            LocalDate.of(2014, 4, 17)
                .atStartOfDay(ZoneId.systemDefault()).toInstant()
        )
    )
    .append(
        "novels", listOf(
            Document("title", "One Hundred Years of Solitude").append("yearPublished", 1967),
            Document("title", "Chronicle of a Death Foretold").append("yearPublished", 1981),
            Document("title", "Love in the Time of Cholera").append("yearPublished", 1985)
        )
    )

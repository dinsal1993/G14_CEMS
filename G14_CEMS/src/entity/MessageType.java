package entity;

public enum MessageType {
	GetAllTests,
	UpdateTestDuration,
	TestsList,
	Error,
	SuccessUpdateTest,
	GetTestCount,
	TestCount,
	GetAllTestBanks,
	LockTest,
	SuccessLockTest,
	GetAllSubjects,
	RequestExtraTime,
	SentExtraTimeRequest,
	RefreshCourseTable,
	CourseList,
	AddCourse,
	CourseAdded,
	DeleteCourse,
	CourseDeleted,
	TestBanksList, QuestionBankList, addQuestion, insertQuestionBank,
	insertTestBank,


	logIn, getTestBankName, getCourseID, getTestID, TestList, CheckTest, CheckValidCode, GetNextQID, GetSubjectID, getCoursesBySubject, GetQuestionsBySubject, GetCourseID, GetTCount, AddTest, Hello, GetQuestionByID, UpdateQuestion,

	 execCode, execCodeManual, downloadManualTest, submitManualTest, DeleteQuestion, GetNextTID, GetAllTestsBySubject, deleteTest, updateTest,

  ContinuePlanTest, InsertPlanTest, SubmitTest, AddStudentToOnGoing, RemoveStudentFromOnGoing , lockTest, AddExecCodeToTestDB, GetTestQuestions, GetTestCode, CheckStudentID, GetExamDate, GotTestCode, TestQuestions, SubmittedTest, GotExamDate, CheckedCode, CheckedStudentID, CheckedTest, AddStudentToOnGoingOnline, RemoveStudentFromOnGoingOnline, GetAllTestsDocsBySubject;

}

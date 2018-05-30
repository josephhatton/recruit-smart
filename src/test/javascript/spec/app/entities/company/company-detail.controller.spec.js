'use strict';

describe('Controller Tests', function() {

    describe('Company Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCompany, MockCompanyComment, MockAddress, MockJobOrder, MockHiringContact, MockCompanyInternalComment, MockActivity, MockSkill;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCompany = jasmine.createSpy('MockCompany');
            MockCompanyComment = jasmine.createSpy('MockCompanyComment');
            MockAddress = jasmine.createSpy('MockAddress');
            MockJobOrder = jasmine.createSpy('MockJobOrder');
            MockHiringContact = jasmine.createSpy('MockHiringContact');
            MockCompanyInternalComment = jasmine.createSpy('MockCompanyInternalComment');
            MockActivity = jasmine.createSpy('MockActivity');
            MockSkill = jasmine.createSpy('MockSkill');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Company': MockCompany,
                'CompanyComment': MockCompanyComment,
                'Address': MockAddress,
                'JobOrder': MockJobOrder,
                'HiringContact': MockHiringContact,
                'CompanyInternalComment': MockCompanyInternalComment,
                'Activity': MockActivity,
                'Skill': MockSkill
            };
            createController = function() {
                $injector.get('$controller')("CompanyDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'recruitsmartApp:companyUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

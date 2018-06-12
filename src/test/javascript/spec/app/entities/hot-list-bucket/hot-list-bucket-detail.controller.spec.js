'use strict';

describe('Controller Tests', function() {

    describe('HotListBucket Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockHotListBucket;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockHotListBucket = jasmine.createSpy('MockHotListBucket');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'HotListBucket': MockHotListBucket
            };
            createController = function() {
                $injector.get('$controller')("HotListBucketDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'recruitsmartApp:hotListBucketUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
